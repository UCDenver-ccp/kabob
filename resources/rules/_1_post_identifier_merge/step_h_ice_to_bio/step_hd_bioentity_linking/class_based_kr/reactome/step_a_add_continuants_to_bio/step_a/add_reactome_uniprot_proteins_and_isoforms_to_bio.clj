`{:description "This rule finds any Uniprot non-isoform protein record described in Reactome with no fragment features or modification features, which allows us to consider it a subclass, not a variant, of the original form.  The rule generates the Reactome protein's ICE identifier, and places the top-level entity on the BIO side as a localized subclass of the BIO entity denoted by the UniProt ICE id.",
 :name "step-hda-reactome_add-reactome-uniprot-proteins-and-isoforms-to-bio",
 :reify ([?/protein_sc {:ln (:sha-1 "bio-side reactome protein" ?/protein_record), :ns "kbio" :prefix "B_"}]),
 :head ((?/reactome_id obo/IAO_0000219 ?/protein_sc)
        (?/protein_sc rdfs/subClassOf ?/protein)),
 :body "PREFIX franzOption_memoryLimit: <franz:85g>
PREFIX franzOption_memoryExhaustionWarningPercentage: <franz:95>
PREFIX franzOption_logQuery: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?reactome_id ?protein ?protein_record
WHERE {
      ?protein_record    rdf:type ccp:IAO_EXT_0001513 . #protein record
      OPTIONAL {
               ?protein_record      obo:BFO_0000051 ?modification_record .
               ?modification_record rdf:type ccp:IAO_EXT_0001527 .
               }
      filter (!bound (?modification_record)) .

      ?protein_record obo:BFO_0000051 ?entity_record .
      ?entity_record rdf:type ccp:IAO_EXT_0001551 . #protein reference record
      ?entity_record obo:BFO_0000051 ?xref_record .
      ?xref_record rdf:type ccp:IAO_EXT_0001588 . #xref field
      ?xref_record obo:BFO_0000051 ?uniprot_field .
      ?uniprot_field rdf:type ccp:IAO_EXT_0001966 . # protein identifier field value
      ?uniprot_field rdf:type ?uniprot_ice .
      ?uniprot_ice rdfs:subClassOf* ccp:IAO_EXT_0001713 .
      ?uniprot_ice obo:IAO_0000219 ?protein .

      ?protein_record obo:BFO_0000051 ?react_xref_record .
      ?react_xref_record rdf:type ccp:IAO_EXT_0001588 . #xref field
      ?react_xref_record obo:BFO_0000051 ?react_id_field .
      ?react_id_field rdf:type ?reactome_id .
      ?reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 .
 }",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}
