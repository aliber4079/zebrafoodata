/*
xmlslurper returns:
GPathResult: name, parent, parents, depth/breadthfirst,
childNodes, children, find/findall, size, text

NodeChild functions
attributes, childNodes, parent, parents, size, text
*/
@Grapes([
	@Grab(group='org.apache.groovy',module='groovy-xml',version='4.0.10'),
	@Grab(group='org.apache.groovy',module='groovy-json',version='4.0.10')
])

import groovy.xml.XmlSlurper
import groovy.xml.XmlParser
import groovy.xml.XmlUtil
import groovy.xml.MarkupBuilder
import groovy.json.JsonOutput
import groovy.xml.slurpersupport.GPathResult
import groovy.xml.slurpersupport.NodeChild
import groovy.xml.slurpersupport.NodeChildren
import groovy.xml.slurpersupport.NamespaceAwareHashMap
def slurper=new XmlSlurper()
slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl",false)
def parser=new XmlParser()
//import groovy.util.Node
def testing=false, xml=null
if (testing) {
	xml=slurper.parseText("""
		<PubmedArticleSet>
  <PubmedArticle>
    <MedlineCitation Status="MEDLINE" IndexingMethod="Curated" Owner="NLM">
      <PMID Version="1">31176586</PMID>
      <DateCompleted>
        <Year>2023</Year>
        <Month>05</Month>
        <Day>18</Day>
      </DateCompleted>
      <DateRevised>
        <Year>2023</Year>
        <Month>05</Month>
        <Day>18</Day>
      </DateRevised>
      <Article PubModel="Print-Electronic">
        <Journal>
          <ISSN IssnType="Electronic">1878-1799</ISSN>
          <JournalIssue CitedMedium="Internet">
            <Volume>33</Volume>
            <Issue>3</Issue>
            <PubDate>
              <Year>2020</Year>
              <Month>May</Month>
            </PubDate>
          </JournalIssue>
          <Title>Women and birth : journal of the Australian College of Midwives</Title>
          <ISOAbbreviation>Women Birth</ISOAbbreviation>
        </Journal>
        <ArticleTitle>Yummy Mummy - The ideal of not looking like a mother.</ArticleTitle>
        <Pagination>
          <MedlinePgn>e266-e273</MedlinePgn>
        </Pagination>
        <ELocationID EIdType="pii" ValidYN="Y">S1871-5192(18)31618-4</ELocationID>
        <ELocationID EIdType="doi" ValidYN="Y">10.1016/j.wombi.2019.05.009</ELocationID>
        <Abstract>
          <AbstractText Label="BACKGROUND" NlmCategory="BACKGROUND">Pregnancy and childbirth are important life experiences that entail major changes, both physically, psychologically, socially and existentially for women. Motherhood transition and the accompanying bodily changes involve expectations of body image that are simultaneously naturally and socially produced and culturally informed by public, private and professional discourses about motherhood transition.</AbstractText>
          <AbstractText Label="PROBLEM" NlmCategory="OBJECTIVE">Much focus is levelled at the antepartum body in maternity services whereas the postpartum body seems left alone, although bodily dissatisfaction is of concern for many mothers, whose expectations of bodily appearance postpartum are sharp and explicit.</AbstractText>
          <AbstractText Label="AIM" NlmCategory="OBJECTIVE">To explore Danish first-time mothers' experiences of their body postpartum, focusing on body image.</AbstractText>
          <AbstractText Label="METHODS" NlmCategory="METHODS">Eleven first-time mothers participated in semi-structured interviews related to the postpartum body image. Data was analysed thematically.</AbstractText>
          <AbstractText Label="FINDINGS" NlmCategory="RESULTS">Four themes: (1) Reverting the body: on bouncing back and losing weight; (2) Picturing me: on standards of beauty and ideal bodies; (3) Redefining earlier self-images: on meta-reproachment of the body; (4) Idealisation of not looking like a mother: on societal pressure to think positively. Findings were discussed through the theoretical concepts by Scheper-Hughes and Lock: the body as both individual, social and political.</AbstractText>
	  <AbstractText Label="CONCLUSION" NlmCategory="CONCLUSIONS">Despite <b>nuanced</b> reflections over the body as subject and object, women identified beauty as a personal trait dependent on visual appearance. Bodily beauty was identified as something individual, yet standardised. Women felt strengthened through motherhood but looking like a mother was not considered worth pursuing. To allow for women's contradictory perspectives, caregivers are advised to communicate reflexively about the postpartum body.</AbstractText>
          <CopyrightInformation>Copyright © 2019 Australian College of Midwives. Published by Elsevier Ltd. All rights reserved.</CopyrightInformation>
        </Abstract>
        <AuthorList CompleteYN="Y">
          <Author ValidYN="Y">
            <LastName>Prinds</LastName>
            <ForeName>Christina</ForeName>
            <Initials>C</Initials>
            <AffiliationInfo>
              <Affiliation>University College South Denmark, Applied Research and Development, Lembckesvej, Haderslev, 6100, Denmark; University of Southern Denmark, Institute of Clinical Research, Research Unit of Gynaecology and Obstetrics, Kløvervænget 10, Odense C., 5000, Denmark. Electronic address: cpri@health.sdu.dk.</Affiliation>
            </AffiliationInfo>
          </Author>
          <Author ValidYN="Y">
            <LastName>Nikolajsen</LastName>
            <ForeName>Helene</ForeName>
            <Initials>H</Initials>
            <AffiliationInfo>
              <Affiliation>University College South Denmark, Department of Physiotherapy, Degnevej, Esbjerg Ø, 6705, Denmark. Electronic address: hnik@ucsyd.dk.</Affiliation>
            </AffiliationInfo>
          </Author>
          <Author ValidYN="Y">
            <LastName>Folmann</LastName>
            <ForeName>Birgitte</ForeName>
            <Initials>B</Initials>
            <AffiliationInfo>
              <Affiliation>University College South Denmark, Applied Research and Development, Lembckesvej, Haderslev, 6100, Denmark. Electronic address: bfol@ucsyd.dk.</Affiliation>
            </AffiliationInfo>
          </Author>
        </AuthorList>
        <Language>eng</Language>
        <PublicationTypeList>
          <PublicationType UI="D016428">Journal Article</PublicationType>
        </PublicationTypeList>
        <ArticleDate DateType="Electronic">
          <Year>2019</Year>
          <Month>06</Month>
          <Day>06</Day>
        </ArticleDate>
      </Article>
      <MedlineJournalInfo>
        <Country>Netherlands</Country>
        <MedlineTA>Women Birth</MedlineTA>
        <NlmUniqueID>101266131</NlmUniqueID>
        <ISSNLinking>1871-5192</ISSNLinking>
      </MedlineJournalInfo>
      <MeshHeadingList>
        <MeshHeading>
          <DescriptorName UI="D000328" MajorTopicYN="N">Adult</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D005260" MajorTopicYN="N">Female</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D006801" MajorTopicYN="N">Humans</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D011247" MajorTopicYN="N">Pregnancy</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D055815" MajorTopicYN="N">Young Adult</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D001828" MajorTopicYN="Y">Body Image</DescriptorName>
          <QualifierName UI="Q000523" MajorTopicYN="N">psychology</QualifierName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D003718" MajorTopicYN="N" Type="Geographic">Denmark</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D007407" MajorTopicYN="N">Interviews as Topic</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D009035" MajorTopicYN="Y">Mothers</DescriptorName>
          <QualifierName UI="Q000523" MajorTopicYN="N">psychology</QualifierName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D036801" MajorTopicYN="Y">Parturition</DescriptorName>
          <QualifierName UI="Q000523" MajorTopicYN="N">psychology</QualifierName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D049590" MajorTopicYN="N">Postpartum Period</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D036301" MajorTopicYN="N">Qualitative Research</DescriptorName>
        </MeshHeading>
        <MeshHeading>
          <DescriptorName UI="D012649" MajorTopicYN="Y">Self Concept</DescriptorName>
        </MeshHeading>
      </MeshHeadingList>
      <KeywordList Owner="NOTNLM">
        <Keyword MajorTopicYN="N">Anthropology</Keyword>
        <Keyword MajorTopicYN="N">Beauty</Keyword>
        <Keyword MajorTopicYN="N">Body image</Keyword>
        <Keyword MajorTopicYN="N">Mothers</Keyword>
        <Keyword MajorTopicYN="N">Postpartum</Keyword>
      </KeywordList>
    </MedlineCitation>
    <PubmedData>
      <History>
        <PubMedPubDate PubStatus="received">
          <Year>2018</Year>
          <Month>10</Month>
          <Day>31</Day>
        </PubMedPubDate>
        <PubMedPubDate PubStatus="revised">
          <Year>2019</Year>
          <Month>05</Month>
          <Day>23</Day>
        </PubMedPubDate>
        <PubMedPubDate PubStatus="accepted">
          <Year>2019</Year>
          <Month>05</Month>
          <Day>23</Day>
        </PubMedPubDate>
        <PubMedPubDate PubStatus="pubmed">
          <Year>2019</Year>
          <Month>6</Month>
          <Day>10</Day>
          <Hour>6</Hour>
          <Minute>0</Minute>
        </PubMedPubDate>
        <PubMedPubDate PubStatus="medline">
          <Year>2020</Year>
          <Month>8</Month>
          <Day>7</Day>
          <Hour>6</Hour>
          <Minute>0</Minute>
        </PubMedPubDate>
        <PubMedPubDate PubStatus="entrez">
          <Year>2019</Year>
          <Month>6</Month>
          <Day>10</Day>
          <Hour>6</Hour>
          <Minute>0</Minute>
        </PubMedPubDate>
      </History>
      <PublicationStatus>ppublish</PublicationStatus>
      <ArticleIdList>
        <ArticleId IdType="pubmed">31176586</ArticleId>
        <ArticleId IdType="pii">S1871-5192(18)31618-4</ArticleId>
        <ArticleId IdType="doi">10.1016/j.wombi.2019.05.009</ArticleId>
      </ArticleIdList>
    </PubmedData>
  </PubmedArticle>
</PubmedArticleSet>
""")
} else {
	xml=slurper.parse(new File("pubmed23n1351.xml"))
}
boolean isTag(GPathResult node) {
	def tags=["b", "i", "sup", "sub", "u"]
	for (def String tag : tags){
		if (node.'*'.find{it.name()==tag}) {
			return true
		}
	}
	return false
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
		if (isTag(node)) {
			result[nodename]=node.text()
			return result
		}
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
xml.'*'.findAll{it.name()=="PubmedArticle"}.each{
	if((iter++ % 1000)==0) {
		if (pw) pw.close()
		outputfilename = "ncbi_bulk_${filecount}.json"
		f=new File(outputfilename)
		pw=new PrintWriter(f)
		filecount++   
	}
	pw.println(JsonOutput.toJson(["create":[:]]))
	pw.println JsonOutput.toJson(parseNode(it))
}
pw.close()
