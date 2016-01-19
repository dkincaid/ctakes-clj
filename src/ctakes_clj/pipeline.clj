(ns ctakes-clj.pipeline
  (:require [uima-clj.uima :as uima]
            [ctakes-clj.component :refer [avro-writer avro-collection-reader]])
(:import com.kincaidweb.uima.cc.AvroFileWriterCasConsumer
         com.kincaidweb.uima.cr.RecordsFromAvroDirectoryCollectionReader))
(def ^:dynamic ctakes-home nil)

(defn run-pipeline
  "Runs the given pipeline in the provided descriptor on the documents in the 
input-dir and writes the resulting CAS XML files to output-dir. Expects that 
system properties ctakes.home, ctakes.umlsuser, ctakes.umlspw are set correctly"
  [descriptor input-dir output-dir codec]
  (let [ctakes-home (System/getProperty "ctakes.home")
        collection-reader (avro-collection-reader ctakes-home input-dir)
        ae-descriptor descriptor
        ae (uima/analysis-engine ae-descriptor)
        file-writer ((avro-writer output-dir codec))]
    (uima/run-pipeline collection-reader [ae file-writer])))

(def clinical-pipeline
  (partial run-pipeline 
           (str (System/getProperty "ctakes.home")
                "/desc/kincaid-pipeline/desc/analysis_engine/KincaidPipeline.xml")))

(def ytex-pipeline
  (partial run-pipeline
           (str (System/getProperty "ctakes.home")
                "/desc/ctakes-ytex-uima/desc/analysis_engine/YTEXPipeline.xml")))




