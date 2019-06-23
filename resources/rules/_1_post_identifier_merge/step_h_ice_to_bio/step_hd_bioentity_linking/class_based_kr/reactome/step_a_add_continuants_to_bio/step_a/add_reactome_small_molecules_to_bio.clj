`{:description "This rule finds any ChEBI small molecule record described in Reactome.  The rule generates the Reactome small molecule's ICE identifier, and places the top-level entity on the BIO side as a localized subclass of the BIO entity denoted by the ChEBI ICE id.",
 :name "step-hda-reactome_add-reactome-small-molecules-to-bio",
 :reify ([?/small_molecule_sc {:ln (:sha-1 "bio-side reactome small molecule" ?/small_molecule_record), :ns "kbio" :prefix "B_"}]),
 :head ((?/reactome_id obo/IAO_0000219 ?/small_molecule_sc)
        (?/small_molecule_sc rdfs/subClassOf ?/small_molecule)),
 :body "PREFIX franzOption_memoryLimit: <franz:85g>
PREFIX franzOption_memoryExhaustionWarningPercentage: <franz:95>
PREFIX franzOption_logQuery: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?reactome_id ?small_molecule ?small_molecule_record
WHERE {
       ?small_molecule_record rdf:type ccp:IAO_EXT_0001514 .  # small molecule record
       ?small_molecule_record obo:BFO_0000051 ?react_xref_record .
       ?react_xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
       ?react_xref_record obo:BFO_0000051 ?react_id_field .
       ?react_id_field rdf:type ?reactome_id .
       ?reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 .

       ?small_molecule_record obo:BFO_0000051 ?entity_record .
       ?entity_record rdf:type ccp:IAO_EXT_0001552 .  # small molecule reference record
       ?entity_record obo:BFO_0000051 ?xref_record .
       ?xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
       ?xref_record obo:BFO_0000051 ?small_molecule_id_field .
       ?small_molecule_id_field rdf:type ccp:IAO_EXT_0001967 . # Reactome small molecule identifier field value
       ?small_molecule_id_field rdf:type ?small_molecule_id .
       ?small_molecule_id obo:IAO_0000219 ?small_molecule .
       filter (contains (str (?small_molecule), \"http://ccp.ucdenver.edu/kabob/bio\")) .
   }",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}
