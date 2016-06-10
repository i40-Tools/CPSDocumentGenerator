# AMLGoldStandardGenerator   
Tool to generate heterogeneity AML files  for data integration.


## Important Dependencies

AMLGoldStandardGenerator needs Java 1.7, Maven 3.0. Download Java SE from  
http://www.oracle.com/technetwork/java/javase/downloads/index.html


## What is AMLGoldStandardGenerator?

AMLGoldStandardGenerator  is a Java based tool , which generates heterogeneity AML files (XML) based on probability and number of child nodes in an AML document. For heterogeneity such as  different Granularity, It perform XML data partition in to two files keeping syntactical consistency of the obtained files against the AutomationML XSD schema. 
The goal of this project is to automate process of creating data heterogeneity in AML files based on randomness and probability.The tool takes input an 'AML file' and outputs 2 files with different heterogeneity which can be used as input for data integration. The integration tool output can then be compared with the orignal input of this tool thus making the process to achieve recall and precision for the obtatined result.


## Build and Setup  

* import as JAVA project in Eclipse or your favourite JAVA IDE.
* RUN as JAVA project.
* output files are stored in output folder.
* Input is stored in resource folder

## Documentation  

Research Documentation available at:   
https://docs.google.com/document/d/1RwsLnVYC9ZFuBUpTo2p_v73yrWLIhxh-xIuTWWdb9dc/edit?usp=sharing

## Usage  

* Give input file Name in main method.
* Run as Java Project
* output is stored in output folder


##License

Copyright (C) 2016 EIS University of Bonn
