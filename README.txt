CPSC 476 - Java Enterprise Application Development
Fall 2012
Starter Code for Problem Set 6

This is a branch of the Sample Code for Problem Set 5 that adds a simple
REST API and includes dependencies for Message Queueing.  You may use
this code as-is to complete Problem Set 6, or add similar functionality
to your own code.

See src/main/scripts for database schema and sample data.

Note that there are a couple of bug fixes/enhancements compared to the
master branch.  These may eventually be merged back into master.

To import this project into STS:
  1. Download the ZIP file from the GitHub page
  2. File Menu -> Import... -> General -> Existing Projects into Workspace
    a. Choose "Select archive file" and browse to the ZIP file
    b. Click Finish

If you need to change the JDK version to 1.6 (e.g., if you're on a Mac)
  1. Open pom.xml
  2. Change the value of the java.version property from 1.7 to 1.6
  3. Right-click the project and choose Maven -> Update Project...
    a. Make sure "Update project configuration from pom.xml" is selected
    b. Click OK

