# Change log 

All notable changes to this project will be documented in this file.

## [Unreleased]

## [v 0.10.1-R0] - 2023-10-16

### Added
- Batch setting up and initializing project.
- Python file management.
- OpenAI API connection, for formatting.
- Python process file into json.
- Java file reader (read file), scan file, process input file from python.
- Resource directories for file management.
- NodeJs directory setup and initialization.

### Fixed
- N/A

### Changed
- N/A

### Removed
- N/A

### Acknowledged
- N/A

## [v 0.10.1-R1] - 2023-10-21

### Added
- Connect Java to MongoDB and test mongodb database query
- Adding AES128 en/decryption for MongoDB
- Adding MongoDB dependency and more
- Adding some python code to help initialize

### Fixed
- Fixing problem with the MongoDB not possible to load uri as datasource.

### Changed
- Refactoring most of the code around SDEFlowService
- ENV Installation Batch script 
- Python Batch script

### Removed
- N/A

### Acknowledged
- https://www.baeldung.com/java-aes-encryption-decryption
- https://stackoverflow.com/questions/28025742/encrypt-and-decrypt-a-string-with-aes-128

## [v 0.10.2-R0] - 2023-10-22

<i>I am planning about Frontend aspect of this software, the design is completed but the code will later be materialized, but only after I am done with the backend.
 '--@Tanawat Boonmak <2023-10-22> </i>

### Added
- Added UtilsMethod move file method to path backup.
- Added more data structure in SharedInstance to work with file.
- Added UtilsMethod to Move file pdf to path ../backup/pdf 
- Several tests for YES flow yield acceptable result.
 
### Fixed
- Refactored ObjectMapper write value because previous result return "" instead of correct jackson.
- Fix file reader not closing properly after reading by gson, which could result in performance issue.
- Restructured of the Autowiring (To prevent circular dependencies, based practice to only use Constructor Injection).
- Fixed issue where the MongoDB could not communicate properly with the MongoDB cloud Atlas.
- Revised some testing criteria and environment variable in local server.

### Changed
- Refactored most of the code around InputFileFlow.
- Refactored CandidateRespositoryImpl.
- Refactored and mapped Candidate class for MongoDB collection.
- Refactored MainController response.

### Removed
- CandidateRespositoryImpl (Replace with autowired Repository directly)
- CandidateInformation (Obsolete)

### Acknowledged
- https://spring.io/guides/gs/accessing-data-mongodb/

## [v 0.10.2-R1] - 2023-10-29

### Added
- Front End Page
- Initialize ExpressJs
- Resume App Page
 
### Fixed
-

### Changed
- 

### Removed
- 

### Acknowledged
-