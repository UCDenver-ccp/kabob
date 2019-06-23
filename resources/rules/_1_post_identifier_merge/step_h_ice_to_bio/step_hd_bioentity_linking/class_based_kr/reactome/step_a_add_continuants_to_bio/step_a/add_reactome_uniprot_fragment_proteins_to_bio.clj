`{:description "This rule finds any Uniprot non-isoform protein record described in Reactome with:
1. a fragment feature field defined as being amino acid 1 to amino acid n, where n is the length of the main isoform, indicating no post-translational cleavage, (which would make it a variant), and 
2. no modification feature field (which would also make it a variant).
The rule generates the Reactome protein's ICE identifier, and places the top-level entity on the BIO side as a localized subclass of the BIO entity denoted by the UniProt ICE id.",
 :name "step-hda-reactome_add-reactome-uniprot-fragment-proteins-to-bio",
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
      ?protein_record rdf:type ccp:IAO_EXT_0001513 .  # protein record
      OPTIONAL {
                ?protein_record obo:BFO_0000051 ?modification_record .
                ?modification_record rdf:type ccp:IAO_EXT_0001586 .  # sequence modification feature fields not allowed, fragment feature fields are ok
                }
      filter (!bound (?modification_record)) .

      ?protein_record obo:BFO_0000051 ?react_xref_record .
      ?react_xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
      ?react_xref_record obo:BFO_0000051 ?react_id_field .
      ?react_id_field rdf:type ?reactome_id .
      ?reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 .

      ?protein_record obo:BFO_0000051 ?entity_record .
      ?entity_record rdf:type ccp:IAO_EXT_0001551 .  # protein reference record
      ?entity_record obo:BFO_0000051 ?xref_record .
      ?xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
      ?xref_record obo:BFO_0000051 ?uniprot_field .
      ?uniprot_field rdf:type ccp:IAO_EXT_0001966 . # protein identifier field value
      ?uniprot_field rdf:type ?uniprot_ice .
      ?uniprot_ice rdfs:subClassOf ccp:IAO_EXT_0000184 .
      ?uniprot_ice obo:IAO_0000219 ?protein .

      ?uniprot_accession_field rdf:type ?uniprot_ice .
      ?uniprot_accession_field rdf:type ccp:IAO_EXT_0000240 .
      ?uniprot_record obo:BFO_0000051 ?uniprot_accession_field .
      ?uniprot_record rdf:type ccp:IAO_EXT_0000234 .
      ?uniprot_record obo:BFO_0000051 ?uniprot_sequence_record .
      ?uniprot_sequence_record rdf:type ccp:IAO_EXT_0001234 . # Uniprot sequence record
      ?uniprot_sequence_record obo:BFO_0000051 ?seq_field .
      ?seq_field rdf:type ccp:IAO_EXT_0001240 . # uniprot sequence record - value field value
      ?seq_field rdfs:label ?sequence_label .
      bind (strlen (?sequence_label) AS ?seq_length) .

      ?protein_record obo:BFO_0000051 ?fragment_feature .
      ?fragment_feature rdf:type ccp:IAO_EXT_0001587 .
      ?fragment_feature obo:BFO_0000051 ?location_interval .
      ?location_interval rdf:type ccp:IAO_EXT_0001576 .
      ?location_interval obo:BFO_0000051 ?start_site .
      ?start_site rdf:type ccp:IAO_EXT_0001534 .
      ?start_site rdf:type ccp:IAO_EXT_0001575 .
      ?start_site obo:BFO_0000051 ?start_position .
      ?start_position rdf:type ccp:IAO_EXT_0001538 .
      ?start_position rdfs:label ?start_coord .
      ?location_interval obo:BFO_0000051 ?end_site .
      ?end_site rdf:type ccp:IAO_EXT_0001535 .
      ?end_site rdf:type ccp:IAO_EXT_0001575 .
      ?end_site obo:BFO_0000051 ?end_position .
      ?end_position rdf:type ccp:IAO_EXT_0001538 .
      ?end_position rdfs:label ?end_coord .
      bind (\"1\"^^xsd:integer as ?one) .
      filter (?start_coord = ?one) .
      filter (?end_coord = ?seq_length) .
      }",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}
