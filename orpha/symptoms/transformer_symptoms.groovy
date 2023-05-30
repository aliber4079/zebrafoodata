@Grab(group='org.apache.groovy',module='groovy-json',version='4.0.12')

import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import java.util.regex.Pattern

def slurper=new JsonSlurper()
def testing=false, json=null
if (testing) {
	json=slurper.parse("""
""")
} else {
	json=slurper.parse(new File("input/hp.json"))
}

def f = null, pw=null, outputfilename=null, line=null, iter=0, filecount=0
json.graphs[0].nodes.findAll{node->
	node["type"]=="CLASS" 
}.each{node->
	if((iter++ % 10000)==0) {
		if (pw) pw.close()
		outputfilename = "symptoms_bulk_${filecount}.json"
		f=new File("output/"+outputfilename)
		pw=new PrintWriter(f)
		filecount++
	}
	pw.println(JsonOutput.toJson(["create" : [:] ]))
	pw.println JsonOutput.toJson([
		"id": node.id.find(/(HP_[0-9]+)/),
		"lbl":node.lbl,
		"synonyms": node.meta?.synonyms*.val ?: []
		])
}
pw.close()

