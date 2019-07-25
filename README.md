## Kayak Flight Reservation Search Automation
    - Go to Kayak.com
    - Enter Origin and Destination Cities
    - Select Departure and Return Dates
    - Select Near by airports for both origin and destination 
    - Click Search
    - On search results page, select the search result N, where N is the input data
    - Assert the Origin and Destination Details are same as the one entered in the main screen
    - Log all critical information of the selected flight option, for the reporting purpose
## Design Considerations
    - Data Driven Framework implemented to read inputs from Excel sheet
    - Page Object Model pattern for minimal impact of object property changes.
    - Extent Reporting for detailed log messages in each test case and HTML reports
    - Test NG
## Steps to clone the tests
    - git clone https://github.com/himabindum/KayakTest.git
    - cd KayakTest
    - mvn clean test
## Results of run
    - Output_ExtentReport has the detailed Test Automation Report for every run
    
    
