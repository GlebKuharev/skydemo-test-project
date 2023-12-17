# Skydemo test project

This is a test demo project for a Playwright framework with Java, Maven and TestNG following the Page Object Model (POM) design pattern. 

## Getting Started

### Installation

1. Create a new repository using this template or clone the repository to your local machine:

   ```bash
   git clone https://github.com/GlebKuharev/skydemo-test-project.git
   ```
2. Navigate to the project directory

   ```bash
   cd skydemo-test-project
   ```
3. Install dependencies

    ```bash
    mvn clean install
    ```
4. Set the `BASE_URL`, `EMAIL` and `PASSWORD` in src/test/resources/config/config.properties 

### Running Tests

To run the tests, use the following Maven command:

```bash
mvn test mvn test -DsuiteFile=src/test/resources/testrunners/{testSuiteName}.xml
```
where {testSuiteName} can be e.g. `regression.xml`

All xml suite files are located under `src/test/resources/testrunners` folder
### Test Artefacts Location

1. **Videos**: ./target/{Date}/Videos
2. **Screenshots**: ./target/{Date}/Screenshots
3. **Playwright Traces**: ./target/{Date}/Traces
4. **logs**: ./target/{Date}/logs

where {Date} is the test suite start timestamp.  
