# AMLGoldStandardGenerator   
Tool to generate Gold Standard for AML files containing different heterogeneity.


## Important Dependencies

AMLGoldStandardGenerator needs Java 1.7, Maven 3.0. Download Java SE from  
http://www.oracle.com/technetwork/java/javase/downloads/index.html


## What is AMLGoldStandardGenerator?

AMLGoldStandardGenerator  is a Java based tool , which generates Gold standard for heterogeneity AML files (XML) based on probability and number of child nodes in an AML document. For heterogeneity such as  different Granularity, It perform XML data partition in to two files keeping syntactical consistency of the obtained files against the AutomationML XSD schema. 
The goal of this project is to automate process of creating data heterogeneity in AML files based on randomness and probability.The tool takes input an 'AML file' and outputs 2 files with different heterogeneity which can be used as input for data integration. The integration tool output can then be compared with the orignal input of this tool thus making the process to achieve recall and precision for the obtatined result.
To create folders manually before running, you can create and put Goldstandard.txt and training data.           

Please include this config.ttl if you want to generate data

```

@prefix aml:     <http://vocab.cs.uni-bonn.de/aml#> .
@prefix het:     <http://vocab.cs.uni-bonn.de/het#> .
@prefix owl:     <http://www.w3.org/2002/07/owl#> .
@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .
@prefix schema:  <http://schema.org/> .
@prefix skos:    <http://www.w3.org/2004/02/skos/core#> .
@prefix xml:     <http://www.w3.org/XML/1998/namespace> .
@prefix xsd:     <http://www.w3.org/2001/XMLSchema#> .
@prefix uri:     <http://uri4uri.net/vocab.html/#>
@prefix sto:     <https://w3id.org/i40/sto#>.
@prefix ontosec: <http://www.semanticweb.org/ontologies/2008/11/OntologySecurity.owl#>

aml:conf 
rdfs:label "General Configuration"@en ;
uri:path "C:/HeterogeneityExampleData/AutomationML/Single-Heterogeneity/M2/Testbeds-1/";  
aml:RandomizeAllSeeds   "false";          

aml:Copyright  "0"; 
aml:Description "0"; 
aml:Version "0"; 
aml:AttributeNameMapping "0"; 
aml:AttributeValueRequirements "0"; 
aml:AttributesetConstraint  "0"; 
aml:MappingObject "0"; 
aml:Attribute "0";

aml:InstanceHierarchy "1";
aml:InstanceHierarchysetInternalElement "1-4";
aml:InstanceHierarchysetInternalElementNested  "2-4";
aml:InstanceHierarchysetInternalRoleRequirement "0";

aml:InternalElementsetAttribute "1"; 

aml:InterfaceClassLib "1";
aml:InterfaceClass "1";
aml:InterfaceClasssetInterfaceClassNested "0";
aml:InterfaceClasssetAttribute "2"; 

aml:RoleClassLib "1";
aml:RoleClass "1";
aml:RoleClassSetAttribute "2";
aml:RoleClassNested "0";
aml:RoleClassSetExternalInterface "0";

aml:SystemUnitClass "1";
aml:SystemUnitClassSetAttribute "1";
aml:SystemUnitClasssetInternalElement "1"; 
aml:SystemUnitClassLib "2";
aml:SystemUnitClasssetSystemUnitClassNested  "0".
 

```



## Build and Setup  
* You can find runnable jar in jar/ folder if you dont want to compile.
## For compilation please do the following :                            
* import as JAVA project in Eclipse or your favourite JAVA IDE.
* RUN as JAVA project.
* output files are stored in output folder.
* Input is stored in resource folder

## Documentation  

Research Documentation available at:   
https://docs.google.com/document/d/1RwsLnVYC9ZFuBUpTo2p_v73yrWLIhxh-xIuTWWdb9dc/edit?usp=sharing

## Usage  

* configure the parameters in configuration.ttl
* Run as Java Project
* output is stored according to configuration.


##License

Copyright (C) 2016 EIS University of Bonn
