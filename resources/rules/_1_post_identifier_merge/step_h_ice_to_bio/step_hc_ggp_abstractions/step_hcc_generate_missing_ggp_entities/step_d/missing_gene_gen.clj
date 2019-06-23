;; --------------------------------------------------
;; --------- Missing gene entity generation ---------
;; --------------------------------------------------
`{:name "step-hcc_missing-gene-gen"
  :description "This rule generates a gene bioentity for any protein that is not connected to a gene via the has_gene_template restriction"
  :head ((?/protein_coding_gene rdfs/subClassOf ?/protein_coding_gene_bioentity)
          (?/taxon_r rdf/type owl/Restriction)
          (?/taxon_r owl/onProperty ?/only_in_taxon) ; RO:only_in_taxon
          (?/taxon_r owl/someValuesFrom ?/taxon)
          (?/protein_coding_gene rdfs/subClassOf ?/taxon_r)
          (?/protein_coding_gene rdfs/label ?/protein_coding_gene_label)
          (?/has_gene_template_r rdf/type owl/Restriction)
          (?/has_gene_template_r owl/onProperty ?/has_gene_template)
          (?/has_gene_template_r owl/someValuesFrom ?/protein_coding_gene)
          (?/protein_missing_gene rdfs/subClassOf ?/has_gene_template_r))
  :reify ([?/taxon_r {:ln (:restriction)
                          :ns "kbio" :prefix "RS_"}]
           [?/has_gene_template_r {:ln (:restriction)
                                            :ns "kbio" :prefix "RS_"}]
           [?/protein_coding_gene {:ln (:sha-1 ?/protein_missing_gene "missing")
                             :ns "kbio" :prefix "B_"}])
  :body "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo: <http://purl.obolibrary.org/obo/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
         select distinct ?protein_missing_gene ?has_gene_template ?protein_coding_gene_bioentity ?only_in_taxon ?taxon ?protein_coding_gene_label {
             ## to keep from climbing the protein hierarchy too high we require the protein to have a taxon
             ?only_in_taxon rdf:type kice:temp_only_in_taxon .
             ?taxon_r owl:onProperty ?only_in_taxon .
             ?taxon_r owl:someValuesFrom ?taxon .
             ?protein_missing_gene rdfs:subClassOf ?taxon_r .
             ?protein_missing_gene rdf:type kice:protein_no_template .
             ?protein_id obo:IAO_0000219 ?protein_missing_gene .
             optional {?protein_missing_gene rdfs:label ?label}

             ?protein_coding_gene_bioentity rdf:type kice:temp_protein_coding_gene .
             ?has_gene_template rdf:type kice:temp_has_gene_template .

             bind(coalesce(?label, \"Unnamed protein\") as ?protein_name)
             bind(concat(\"Gene coding \", str(?protein_name)) as ?protein_coding_gene_label)
         }"
  }

