#!/bin/bash

# most downloads are handled by the file parser library, however the irefweb downloads sometimes terminate
# prematurely so we use wget here to download the human irefweb file. The interpro downloads also have issues from
# time to time, so they are downloaded here too just to be safe.
mkdir -p /kabob_data/raw/irefweb
mkdir -p /kabob_data/raw/interpro

DATE=$(date +%m/%d/%Y)

# wget the irefweb file using an automated retry-on-failure flag
cd /kabob_data/raw/irefweb && { wget -c -t 0 --timeout 60 --waitretry 10 http://irefindex.org/download/irefindex/data/archive/release_15.0/psi_mitab/MITAB2.6/9606.mitab.22012018.txt.zip ; unzip 9606.mitab.22012018.txt.zip ; cd - ; }
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/irefweb/9606.mitab.01-22-2018.txt  http://irefindex.org/download/irefindex/data/archive/release_15.0/psi_mitab/MITAB2.6/9606.mitab.22012018.txt.zip

# get the interpro.xml file
cd /kabob_data/raw/interpro && { wget -c -t 0 --timeout 60 --waitretry 10 ftp://ftp.ebi.ac.uk/pub/databases/interpro/interpro.xml.gz ; cd - ; }
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/interpro/interpro.xml.gz  ftp://ftp.ebi.ac.uk/pub/databases/interpro/interpro.xml.gz

# get the interpro protein2ipr.dat.gz file
cd /kabob_data/raw/interpro && { wget -c -t 0 --timeout 60 --waitretry 10 ftp://ftp.ebi.ac.uk/pub/databases/interpro/protein2ipr.dat.gz ; cd - ; }
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/interpro/protein2ipr.dat.gz  ftp://ftp.ebi.ac.uk/pub/databases/interpro/protein2ipr.dat.gz


# there are also resources that are not processed by the file parsers directly, e.g. the Reactome biopax OWL file.
# These resources are downloaded here and placed into the /kabob_data/rdf/ directory so that they are loaded
# automatically.

# wget Reactome in BioPax format
mkdir -p /kabob_data/raw/reactome
cd /kabob_data/raw/reactome && { wget -c -t 0 --timeout 60 --waitretry 10 http://www.reactome.org/download/current/biopax.zip ; unzip -o biopax.zip ; cd - ; }
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/reactome/Homo_sapiens.owl  http://www.reactome.org/download/current/biopax.zip
/kabob.git/scripts/download/create-metadata-rdf.sh /kabob_data/raw/reactome/Homo_sapiens.owl REACTOME http://www.reactome.org/download/current/biopax.zip
mkdir -p /kabob_data/rdf/reactome
# copy the human reactome OWL file to the /kabob_data/rdf/reactome directory so that it will be loaded automatically
cp /kabob_data/raw/reactome/Homo_sapiens.owl /kabob_data/rdf/reactome/Homo_sapiens.owl
# copy the human reactome OWL metadata RDF file to the /kabob_data/rdf/reactome directory so that it will be loaded automatically
cp /kabob_data/raw/reactome/Homo_sapiens.owl.ready.nt /kabob_data/rdf/reactome/Homo_sapiens.owl.ready.nt
gzip /kabob_data/rdf/reactome/Homo_sapiens.owl.ready.nt


# get the bioGRID files
mkdir -p /kabob_data/raw/biogrid
cd /kabob_data/raw/biogrid && { wget -c -t 0 --timeout 60 --waitretry 10 https://downloads.thebiogrid.org/Download/BioGRID/Latest-Release/BIOGRID-ALL-LATEST.tab2.zip ; unzip -o BIOGRID-ALL-LATEST.tab2.zip ; mv BIOGRID-ALL-*.tab2.txt BIOGRID-ALL-LATEST.tab2.txt ; wget -c -t 0 --timeout 60 --waitretry 10 https://downloads.thebiogrid.org/Download/BioGRID/Latest-Release/BIOGRID-CHEMICALS-LATEST.chemtab.zip ; unzip -o BIOGRID-CHEMICALS-LATEST.chemtab.zip ; mv BIOGRID-CHEMICALS-*.chemtab.txt BIOGRID-CHEMICALS-LATEST.chemtab.txt ; wget -c -t 0 --timeout 60 --waitretry 10 https://downloads.thebiogrid.org/Download/BioGRID/Latest-Release/BIOGRID-MV-Physical-LATEST.tab2.zip ; unzip -o BIOGRID-MV-Physical-LATEST.tab2.zip ; mv BIOGRID-MV-Physical-*.tab2.txt BIOGRID-MV-Physical-LATEST.tab2.txt ; cd - ; }
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/biogrid/BIOGRID-MV-Physical-LATEST.tab2.txt  https://downloads.thebiogrid.org/Download/BioGRID/Latest-Release/BIOGRID-MV-Physical-LATEST.tab2.zip
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/biogrid/BIOGRID-ALL-LATEST.tab2.txt  https://downloads.thebiogrid.org/Download/BioGRID/Latest-Release/BIOGRID-ALL-LATEST.tab2.zip
/kabob.git/scripts/download/create-metadata-file.sh /kabob_data/raw/biogrid/BIOGRID-CHEMICALS-LATEST.chemtab.txt  https://downloads.thebiogrid.org/Download/BioGRID/Latest-Release/BIOGRID-CHEMICALS-LATEST.chemtab.zip


# get various identifier mapping files from BioMart
mkdir -p /kabob_data/raw/biomart
cd /kabob_data/raw/biomart && { wget -O /kabob_data/raw/biomart/biomart-gene-identifier-mappings.txt 'http://www.ensembl.org/biomart/martservice?query=<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE Query><Query  virtualSchemaName = "default" formatter = "TSV" header = "0" uniqueRows = "0" count = "" datasetConfigVersion = "0.6" >	<Dataset name = "hsapiens_gene_ensembl" interface = "default" >		<Attribute name = "ensembl_gene_id" />	<Attribute name = "entrezgene" /> <Attribute name = "hgnc_id" /> </Dataset></Query>'; cd - ;}
cd /kabob_data/raw/biomart && { wget -O /kabob_data/raw/biomart/biomart-transcript-identifier-mappings.txt 'http://www.ensembl.org/biomart/martservice?query=<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE Query><Query  virtualSchemaName = "default" formatter = "TSV" header = "0" uniqueRows = "0" count = "" datasetConfigVersion = "0.6" >	<Dataset name = "hsapiens_gene_ensembl" interface = "default" >		<Attribute name = "ensembl_gene_id" />		<Attribute name = "ensembl_transcript_id" /> <Attribute name = "rnacentral" /> </Dataset></Query>'; cd - ;}
cd /kabob_data/raw/biomart && { wget -O /kabob_data/raw/biomart/biomart-protein-identifier-mappings.txt 'http://www.ensembl.org/biomart/martservice?query=<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE Query><Query  virtualSchemaName = "default" formatter = "TSV" header = "0" uniqueRows = "0" count = "" datasetConfigVersion = "0.6" >	<Dataset name = "hsapiens_gene_ensembl" interface = "default" >		<Attribute name = "ensembl_gene_id" /> <Attribute name = "ensembl_peptide_id" /> <Attribute name = "uniprotswissprot" /> <Attribute name = "uniprotsptrembl" /> </Dataset></Query>'; cd - ;}
cd /kabob_data/raw/biomart && { wget -O /kabob_data/raw/biomart/biomart-central-dogma-linkages.txt 'http://www.ensembl.org/biomart/martservice?query=<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE Query><Query  virtualSchemaName = "default" formatter = "TSV" header = "0" uniqueRows = "0" count = "" datasetConfigVersion = "0.6" >	<Dataset name = "hsapiens_gene_ensembl" interface = "default" >		<Attribute name = "ensembl_gene_id" /> <Attribute name = "ensembl_transcript_id" /> <Attribute name = "ensembl_peptide_id" /> </Dataset></Query>'; cd - ;}