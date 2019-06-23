`{:description "This rule finds any modified EMBL rna record described in Reactome.  The rule generates the Reactome rna's ICE identifier, and places the top-level entity on the BIO side as a localized subclass of the BIO entity denoted by the EMBL ICE id.  It also makes the denotational link to a new EMBL BIO entity if one doesn't exist already.",
 :name "step-hda-reactome_add-reactome-variant-rnas-to-bio",
 :reify ([?/rna_sc_variant {:ln (:sha-1 "bio-side modified reactome rna" ?/rna_record), :ns "kbio" :prefix "B_"}]
         [?/rna_sc {:ln (:sha-1 "bio-side reactome rna" ?/rna_record), :ns "kbio" :prefix "B_"}]),
 :head ((?/reactome_id obo/IAO_0000219 ?/rna_sc_variant)
        (?/rna_sc rdfs/subClassOf ?/rna)
        (?/rna_sc_variant rdfs/subClassOf ?/variant_restriction)
        (?/variant_restriction owl/onProperty ?/variant_of)
        (?/variant_restriction owl/someValuesFrom ?/rna_sc)
        (?/variant_restriction rdf/type owl/Restriction)),
 :body "PREFIX franzOption_memoryLimit: <franz:85g>
PREFIX franzOption_memoryExhaustionWarningPercentage: <franz:95>
PREFIX franzOption_logQuery: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?reactome_id ?rna_id ?rna_record
WHERE {
      ?rna_record rdf:type ccp:IAO_EXT_0001558 .  # rna record
      # make sure there this is an RNA variant
      OPTIONAL {
                ?rna_record obo:BFO_0000051 ?modification_feature_record .
                ?modification_feature_record rdf:type ccp:IAO_EXT_0001586 . #  modification features allowed
                }
      OPTIONAL {
                ?rna_record obo:BFO_0000051 ?fragment_feature_record .
                ?fragment_feature_record rdf:type ccp:IAO_EXT_0001587 .
                }
      filter (bound (?modification_feature_record) || bound (?fragment_feature_record)) .

                  ?rna_record obo:BFO_0000051 ?react_xref_record .
                  ?react_xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
      ?react_xref_record obo:BFO_0000051 ?react_id_field .
      ?react_id_field rdf:type ?reactome_id .
      ?reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 .

      ?rna_record obo:BFO_0000051 ?entity_record .
      ?entity_record rdf:type ccp:IAO_EXT_0001561 .  # rna reference record
      ?entity_record obo:BFO_0000051 ?xref_record .
      ?xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
      ?xref_record obo:BFO_0000051 ?rna_id_field .
      ?rna_id_field rdf:type ccp:IAO_EXT_0001969 . # Reactome RNA identifier field value
      ?rna_id_field rdf:type ?rna_id .
      ?rna_id obo:IAO_0000219 ?rna .
{
         select ?variant_of {
                             <http://ccp.ucdenver.edu/kabob/ice/so#variant_of> obo:IAO_0000219 ?variant_of .
                                     filter (?variant_of != <http://purl.obolibrary.org/obo/so#variant_of>) .
                             }
         }
}",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}
