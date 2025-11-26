/**
 * Jest configuration for BookingMx frontend testing.
 * Configures test environment, coverage thresholds, and file patterns.
 * 
 * @type {import('jest').Config}
 */
export default {
  // Use Node environment for ES modules support
  testEnvironment: 'node',
  
  // Don't transform files (use native ES modules)
  transform: {},
  
  // File extensions to consider
  moduleFileExtensions: ['js', 'mjs'],
  
  // Pattern to find test files
  testMatch: [
    '**/__tests__/**/*.test.js',
    '**/?(*.)+(spec|test).js'
  ],
  
  // Files to collect coverage from
  collectCoverageFrom: [
    'src/**/*.js',
    'js/*.js',
    '!src/**/*.test.js',
    '!src/app.js',           // Exclude UI logic
    '!src/styles.css'        // Exclude CSS
  ],
  
  // Coverage thresholds (minimum 90%)
  coverageThreshold: {
    global: {
      branches: 90,
      functions: 90,
      lines: 90,
      statements: 90
    }
  },
  
  // Coverage output directory
  coverageDirectory: 'coverage',
  
  // Coverage reporters
  coverageReporters: [
    'text',           // Console output
    'text-summary',   // Summary in console
    'html',           // HTML report
    'lcov'            // For CI/CD tools
  ],
  
  // Verbose output
  verbose: true,
  
  // Clear mocks between tests
  clearMocks: true,
  
  // Restore mocks after each test
  restoreMocks: true
};