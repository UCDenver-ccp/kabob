`{:name        "virtual-graph-generation"
  :description "Creates the virtual graph construct so that queries can cross named graphs."
  :head        ((bd/vg bd/virtualGraph ?/g))
  :body "SELECT DISTINCT ?g { GRAPH ?g {?s ?p ?o}}"
  }
