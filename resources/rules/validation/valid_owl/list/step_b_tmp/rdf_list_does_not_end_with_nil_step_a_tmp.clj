`{:name        "validation_rdf-list-does-not-end-with-nil-step-b-tmp"
  :description "Marks RDF list nodes that properly end using rdf:nil"
  :head        ((?/list rdf/type ccp/properlist))
  :body "select ?list {
                    ?list rdf:type rdf:List .
                    ?list rdf:rest+ rdf:nil
                }"
  }
