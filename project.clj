(defproject ctakes-clj "0.1.0-SNAPSHOT"
  :description "Library for making working with cTAKES easier in Clojure"
  :url "http://dkincaid.github.io/ctakes-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src"]
  :codox {:output-path "doc"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [uima-clj/uima-clj "0.1.0-SNAPSHOT"]
                 [com.kincaidweb/uima-components "1.1-SNAPSHOT"]]
  :profiles {:dev {:resource-paths ["resources-ctakes"]
                   :dependencies [[midje "1.8.2"]]}
             :provided {:dependencies [[org.apache.ctakes/ctakes-core "3.2.3-SNAPSHOT" :exclusions [org.apache.uima/*]]
                                       [org.apache.ctakes/ctakes-type-system "3.2.3-SNAPSHOT"]
                                       [org.apache.ctakes/ctakes-clinical-pipeline "3.2.3-SNAPSHOT"]
                                       [org.apache.ctakes/ctakes-lvg "3.2.3-SNAPSHOT"]
                                       [org.cleartk/cleartk-ml "2.0.0"]
                                       [org.cleartk/cleartk-ml-liblinear "2.0.0"]
                                       [net.sourceforge.openai/openaifsm "0.0.1"]
                                       [org.mitre/medfacts-zoner "1.1"]
                                       [org.mitre/medfacts-i2b2 "1.2-SNAPSHOT"]
                                       [org.mitre/jcarafe "2.9.1-0.9.8.3.RC4"]
                                       [org.mitre/jcarafe-ext "2.9.1-0.9.8.3.RC4"]
                                       [org.scala-lang/scala-library "2.9.0"]
                                       [org.scala-tools.sbinary/sbinary_2.9.0 "0.4.0"]
                                       [gov.nih.nlm.nls.lvg/lvg2010dist "0.0.1"]
                                       [org.codehaus.jackson/jackson-core-asl "1.5.0"]
                                       [org.codehaus.jackson/jackson-mapper-asl "1.5.0"]
                                       [commons-cli/commons-cli "1.2"]
                                       [org.testng/testng "6.1.1"]]}
             }
  )
