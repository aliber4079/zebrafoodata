/*
 xmlslurper returns:
 GPathResult: name, parent, parents, depth/breadthfirst,
 childNodes, children, find/findall, size, text
 
 NodeChild functions
 attributes, childNodes, parent, parents, size, text
 */
 @Grapes([
	 @Grab(group='org.apache.groovy',module='groovy-xml',version='4.0.12'),
	 @Grab(group='org.apache.groovy',module='groovy-json',version='4.0.12')
 ])
 
 import groovy.xml.XmlSlurper
 import groovy.json.JsonOutput
 import groovy.xml.slurpersupport.GPathResult
 import groovy.xml.slurpersupport.NodeChild
 import groovy.xml.slurpersupport.NodeChildren
 import groovy.xml.slurpersupport.NamespaceAwareHashMap
 def slurper=new XmlSlurper()
 def testing=false, xml=null
 if (testing) {
	 xml=slurper.parseText("""
		<JDBOR date="2022-06-14 16:21:21" version="1.3.16 / 4.1.7 [2022-01-26] (orientdb version)" copyright="Orphanet (c) 2022" dbserver="jdbc:sybase:Tds:canard.orpha.net:2020">
		<Availability>
		<Licence>
		<FullName lang="en">Creative Commons Attribution 4.0 International</FullName>
		<ShortIdentifier>CC-BY-4.0</ShortIdentifier>
		<LegalCode>https://creativecommons.org/licenses/by/4.0/legalcode</LegalCode>
		</Licence>
		</Availability>
		<HPODisorderSetStatusList count="4245">
		<HPODisorderSetStatus id="1">
		<Disorder id="2">
		<OrphaCode>58</OrphaCode>
		<ExpertLink lang="en">http://www.orpha.net/consor/cgi-bin/OC_Exp.php?lng=en&amp;Expert=58</ExpertLink>
		<Name lang="en">Alexander disease</Name>
		<DisorderType id="21394">
		<Name lang="en">Disease</Name>
		</DisorderType>
		<DisorderGroup id="36547">
		<Name lang="en">Disorder</Name>
		</DisorderGroup>
		<HPODisorderAssociationList count="61">
		<HPODisorderAssociation id="327485">
		<HPO id="4">
		<HPOId>HP:0000256</HPOId>
		<HPOTerm>Macrocephaly</HPOTerm>
		</HPO>
		<HPOFrequency id="28412">
		<Name lang="en">Very frequent (99-80%)</Name>
		</HPOFrequency>
		<DiagnosticCriteria/>
		</HPODisorderAssociation>
		<HPODisorderAssociation id="327486">
		<HPO id="18">
		<HPOId>HP:0001249</HPOId>
		<HPOTerm>Intellectual disability</HPOTerm>
		</HPO>
		<HPOFrequency id="28412">
		<Name lang="en">Very frequent (99-80%)</Name>
		</HPOFrequency>
		<DiagnosticCriteria/>
		</HPODisorderAssociation>
		</HPODisorderAssociationList>
		</Disorder>
		</HPODisorderSetStatus>
		</HPODisorderSetStatusList>
	</JDBOR>""")
 } else {
	 xml=slurper.parse(new File("input/en_product4.xml"))
 }
 
 Object parseNode(GPathResult node) {
	 def result=null
	 if (node instanceof NodeChildren) {
		 if(node.size()==1) {
			 return parseNode(node[0])
		 }
		 result=[:]
		 node.each{child->
			 result[child.name()]=parseNode(child)
		 }
		 return result
	 }
	 if (node instanceof NodeChild) {
		 if (!result) {
			 result=[(node.name()):null]
		 }
		 if (node.attributes().size()) {
				 if (!result[(node.name())]) {
				 result[(node.name())]=[:]
			 }
			 node.attributes().each { k, v ->
				 result[(node.name())][(k)]=v
			 }
		 }
		 if (node.localText().size()) {
			 def text=node.localText().join().trim()
			 if (!result[(node.name())]) {
				 result[(node.name())]=text
			 } else {
				 result[(node.name())]=text
			 }
		 }
	 }
	 if (node.children().size()>0) {
		 def String nodename=node.name()
		 //>1 child with the same properties?
		 if (node.children().size()>1 && (node.children()[0].name()==node.children()[1].name())) {
			 result[nodename]=[]
		 } else {
			 result[nodename]=[:]
			 if (node.attributes().size()) {
				 node.attributes().each { k, v ->
					 result[(node.name())][(k)]=v
				 }
			 }
		 }
		 node.children().each{child->
		 def stufftoadd=parseNode(child)
		 stufftoadd.each {k,v->
			 if (result[nodename] instanceof List) {
				 result[nodename].push([(k):v])
			 } else {
				 result[nodename][k]=v
			 }
		 }
	 }
 }
	 return result
 }
 def f = null, pw=null, outputfilename=null, line=null, iter=0, filecount=0
 xml.'**'.findAll{it.name()=="Disorder"}.each {it1->
	 if((iter++ % 1000)==0) {
		 if (pw) pw.close()
		 outputfilename = "diseases_bulk_${filecount}.json"
		 f=new File("output/"+outputfilename)
		 pw=new PrintWriter(f)
		 filecount++
	 }
	 pw.println JsonOutput.toJson(["create" : [:] ])
	 def json=parseNode(it1)
	 def agg=[:]
	 def json1=json.Disorder.HPODisorderAssociationList.each{it2->
		 try {
			 if (agg[it2.HPODisorderAssociation.HPOFrequency.id]==null) {
				 agg[it2.HPODisorderAssociation.HPOFrequency.id]=[it2.HPODisorderAssociation.HPO.HPOId]
			 } else {
				 agg[it2.HPODisorderAssociation.HPOFrequency.id].push(it2.HPODisorderAssociation.HPO.HPOId)
			 }
		 } catch(Exception e) {
			 println "oops " + it2
		 }
	 }
	 json.Disorder.freq_agg=agg
	 pw.println JsonOutput.toJson(json)
 }
 pw.close()
 