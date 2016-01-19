(ns ctakes-clj.annotation
(:require [uima-clj.uima :as uima :refer [jcas-type-seq]])
(:import org.apache.ctakes.typesystem.type.syntax.BaseToken
         [org.apache.ctakes.typesystem.type.textsem DiseaseDisorderMention IdentifiedAnnotation]
         org.apache.ctakes.typesystem.type.textspan.Sentence
         org.apache.uima.fit.util.JCasUtil))

;;; Functions to get various types of annotations from a CAS
(def disease-disorders
  "Get all the DiseaseDisorderMentions in the JCas."
  (partial jcas-type-seq DiseaseDisorderMention))

(def sentences
  "Get all the sentence annotations in the JCas"
  (partial jcas-type-seq Sentence))

(def base-tokens
  "Get all the BaseToken annotations in the JCas"
  (partial jcas-type-seq BaseToken))

(def identified-annotations
  "Get all the IdentifiedAnnotation annotations in the JCas"
  (partial jcas-type-seq IdentifiedAnnotation))

(defn concepts
  "Get all the UMLS concept annotations from the given IdentifiedAnnotation"
  [identified-annotation]
  (if-let [fs-array (.getOntologyConceptArr identified-annotation)]
    (for [i (range (.size fs-array))]
      (.get fs-array i))))

(defn tokens-in-sentence
  "Returns all the BaseToken annotations that are within a Sentence annotation."
  [sentence]
  (JCasUtil/selectCovered BaseToken sentence))

