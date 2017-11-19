<?xml version="1.0" encoding="UTF-8"?>
 <graphml xmlns="http://graphml.graphdrawing.org/xmlns"  
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns 
     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">
   <key id="d0" for="node" attr.name="color" attr.type="string">
     <default>yellow</default>
   </key>
   <key id="d1" for="edge" attr.name="weight" attr.type="double"/>
   <graph id="G" edgedefault="undirected">
     <node id="n0">
       <data key="d0">green</data>
     </node>
     <node id="n1"/>
     <node id="n2">
       <data key="d0">blue</data>
     </node>
     <node id="n3">
       <data key="d0">red</data>
     </node>
     <node id="n4"/>
     <node id="n5">
       <data key="d0">turquoise</data>
     </node>
     <edge id="e0" source="n0" target="n2">
       <data key="d1">1.0</data>
     </edge>
     <edge id="e1" source="n0" target="n1">
       <data key="d1">1.0</data>
     </edge>
     <edge id="e2" source="n1" target="n3">
       <data key="d1">2.0</data>
     </edge>
     <edge id="e3" source="n3" target="n2"/>
     <edge id="e4" source="n2" target="n4"/>
     <edge id="e5" source="n3" target="n5"/>
     <edge id="e6" source="n5" target="n4">
       <data key="d1">1.1</data>
     </edge>
   </graph>
 </graphml>
