// Graph data structures and algorithms for nearby cities.
// Keep functions pure for easy Jest unit testing.

export class Graph {
  constructor() {
    this.adj = new Map(); // city -> Array<{to, distance}>
  }

  addCity(name) {
    if (!name || typeof name !== "string") throw new Error("Invalid city name");
    if (!this.adj.has(name)) this.adj.set(name, []);
  }

  addEdge(from, to, distanceKm) {
    if (!this.adj.has(from) || !this.adj.has(to)) throw new Error("Unknown city");
    if (!Number.isFinite(distanceKm) || distanceKm < 0) throw new Error("Invalid distance");
    this.adj.get(from).push({ to, distance: distanceKm });
    this.adj.get(to).push({ to: from, distance: distanceKm }); // undirected
  }

  neighbors(city) {
    if (!this.adj.has(city)) throw new Error("Unknown city");
    return [...this.adj.get(city)];
  }
}

// Validates input dataset (array of cities, array of edges)
export function validateGraphData({ cities, edges }) {
  if (!Array.isArray(cities) || !Array.isArray(edges)) return { ok: false, reason: "cities/edges must be arrays" };
  const citySet = new Set(cities);
  if (citySet.size !== cities.length) return { ok: false, reason: "duplicate cities" };
  for (const c of cities) if (typeof c !== "string" || !c.trim()) return { ok: false, reason: "invalid city entry" };
  for (const e of edges) {
    const { from, to, distance } = e ?? {};
    if (!citySet.has(from) || !citySet.has(to)) return { ok: false, reason: "edge references unknown city" };
    if (!Number.isFinite(distance) || distance < 0) return { ok: false, reason: "invalid distance" };
  }
  return { ok: true };
}

export function buildGraph(cities, edges) {
  const g = new Graph();
  for (const c of cities) g.addCity(c);
  for (const { from, to, distance } of edges) g.addEdge(from, to, distance);
  return g;
}

// Returns nearby cities within <= maxDistance (direct connections only, for MVP)
export function getNearbyCities(graph, destination, maxDistanceKm = 250) {
  if (!(graph instanceof Graph)) throw new Error("graph must be Graph");
  if (typeof destination !== "string" || !graph.adj.has(destination)) return [];
  const neighbors = graph.neighbors(destination);
  return neighbors
    .filter(n => n.distance <= maxDistanceKm)
    .sort((a,b) => a.distance - b.distance)
    .map(n => ({ city: n.to, distance: n.distance }));
}

// Sample dataset
export const sampleData = {
  cities: [
    "Guadalajara", "Tlaquepaque", "Zapopan", "Tepatitlán", "Lagos de Moreno", "Tala", "Tequila"
  ],
  edges: [
    { from: "Guadalajara", to: "Zapopan", distance: 12 },
    { from: "Guadalajara", to: "Tlaquepaque", distance: 10 },
    { from: "Guadalajara", to: "Tepatitlán", distance: 78 },
    { from: "Guadalajara", to: "Tequila", distance: 60 },
    { from: "Zapopan", to: "Tala", distance: 35 },
    { from: "Tepatitlán", to: "Lagos de Moreno", distance: 85 }
  ]
};
