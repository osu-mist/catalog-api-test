/*
Brandon Lee
OSU Scraper Test
Things I want to scrape: Course Name, Course Description, Instructor, CRN
*/

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Scrape{

    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("http://catalog.oregonstate.edu/CourseList.aspx?subjectcode=CS&level=undergrad&campus=corvallis").get();
            org.jsoup.select.Elements links = doc.select("a");
            for(Element e: links){
                System.out.println(e.attr("abs:href"));
            }
        }
        catch(IOException ex){
            System.out.println("Error: Did not find link");
            //Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//-------------------------
//Early script attempts
/*
def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
def slurper = new XmlSlurper(tagsoupParser)
def htmlParser = slurper.parse(config.clientUrl)
ArrayList inputs = new ArrayList();

htmlParser.'**'.findAll{
    it.name() == 'input' || it.name() == 'select'
}.each {
    if (it.attributes().get(id) ) {
        inputs.add(it)
    }
}

def url = "http://catalog.oregonstate.edu/CourseDetail.aspx?subjectcode=CS&coursenumber=101"

print "test"
*/