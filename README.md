The KaBOB Knowledge Base of Biology is a semantic integration of a variety of molecular biology resources, grounded in the Open Biomedical Ontologies. This wiki contains instructions for building and using KaBOB.

For background and citation purposes, please see the [original KaBOB publication](https://www.ncbi.nlm.nih.gov/pubmed/25903923):
> KaBOB: Ontology-Based Semantic Integration of Biomedical Databases <br/>
Kevin M Livingston, Michael Bada, William A Baumgartner Jr., and Lawrence E Hunter <br/> 
BMC Bioinformatics 2015 Apr 23;16:126. doi: 10.1186/s12859-015-0559-3

# Active Branch
Please note that the active development branch is currently [bg-integration](https://github.com/UCDenver-ccp/kabob/tree/bg-integration). This branch uses Blazegraph as the backend triple store, and should be used when installing and building KaBOB.

# System Requirements
* The recommended installation method makes use of Docker, thus [Docker](https://www.docker.com/) is the major system requirement. 
* Running on a machine with a relatively large amount of RAM is also a requirement. The current setup has been tested using 60GB allocated to Blazegraph. This is likely higher than what is truly necessary. Further tests will be conducted to better define the memory requirements for building KaBOB.

# Installation
Please see the dedicated [installation wiki page](https://github.com/UCDenver-ccp/kabob/wiki/Installation-via-Docker) for details on installing and building an instance of KaBOB.

# Knowledge Representation
In order to effectively use KaBOB, an understanding of how it represents various aspects of biology is required. KaBOB uses [OWL](https://www.w3.org/TR/owl2-primer/) to formulate its representations of biology. Please see the dedicated [knowledge representation wiki page](https://github.com/UCDenver-ccp/kabob/wiki/KaBOB-Knowledge-Representation) for further information.

# Querying KaBOB
Please find instructions for submitting queries on the [querying KaBOB wiki page](https://github.com/UCDenver-ccp/kabob/wiki/Querying-KaBOB), and see the dedicated [sample queries wiki page](https://github.com/UCDenver-ccp/kabob/wiki/Sample-Queries) for examples queries.

# Contact Information
For questions about building or querying KaBOB, please [submit an issue](https://github.com/UCDenver-ccp/kabob/issues) to this GitHub repository.
