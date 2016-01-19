(ns ctakes-clj.component
(:require [uima-clj.uima :as uima])
(:import com.kincaidweb.uima.cc.AvroFileWriterCasConsumer
         com.kincaidweb.uima.cr.RecordsFromAvroDirectoryCollectionReader
         org.apache.ctakes.chunker.ae.Chunker
         org.apache.ctakes.clinicalpipeline.ClinicalPipelineFactory
         org.apache.ctakes.contexttokenizer.ae.ContextDependentTokenizerAnnotator
         [org.apache.ctakes.core.ae SentenceDetector SimpleSegmentAnnotator TokenizerAnnotatorPTB]
         org.apache.ctakes.dictionary.lookup2.ae.DefaultJCasTermAnnotator
         org.apache.ctakes.drugner.ae.DrugMentionAnnotator
         org.apache.ctakes.lvg.ae.LvgAnnotator
         org.apache.ctakes.necontexts.ContextAnnotator
         org.apache.ctakes.postagger.POSTagger))

(defn avro-writer
  [directory codec]
  (uima/analysis-engine AvroFileWriterCasConsumer
                        [AvroFileWriterCasConsumer/PARAM_FILENAME directory
                         AvroFileWriterCasConsumer/PARAM_DOCUMENT_ID_FIELD "documentId"
                         AvroFileWriterCasConsumer/PARAM_CAS_FIELD "cas"
                         AvroFileWriterCasConsumer/PARAM_CODEC codec]))


(defn avro-collection-reader
  [input-dir]
  (uima/collection-reader RecordsFromAvroDirectoryCollectionReader
                          ["DirectoryName" input-dir
                           "DocumentIdField" "mnRowKey"
                           "ContentField" "mnText"]))

;;; UIMA Annotator Components
(defn simple-segment
  "Creates a single segment annotation around the entire document."
  ([] (SimpleSegmentAnnotator/createAnnotatorDescription))
  ([segment-id] (SimpleSegmentAnnotator/createAnnotatorDescription segment-id)))

(defn sentence-detector
  "Discovers sentence boundaries."
  ([] (SentenceDetector/createAnnotatorDescription))

  ([model-file] (sentence-detector model-file []))

  ([model-file segments-to-skip]
   (uima/ae-desc SentenceDetector
                 [SentenceDetector/PARAM_SD_MODEL_FILE model-file
                  SentenceDetector/PARAM_SEGMENTS_TO_SKIP (into-array String segments-to-skip)])))

(defn tokenizer-ptb
  "Discovers tokens in the given text, following Penn TreeBank tokenization rules.  These tokens consist of words, punctuation, etc..."
  ([] (TokenizerAnnotatorPTB/createAnnotatorDescription))

  ([segments-to-skip] (uima/ae-desc TokenizerAnnotatorPTB
                                    [TokenizerAnnotatorPTB/PARAM_SEGMENTS_TO_SKIP (into-array String segments-to-skip)])))

(defn lvg-annotator
  "UIMA annotator that uses the UMLS LVG package to find the canonical form of WordTokens. The
  package is also used to find one or more lemmas for a given WordToken along with its associated
  part of speech."
  ([] (LvgAnnotator/createAnnotatorDescription)))

(defn context-dependent-tokenizer-annotator
  "Finds tokens based on context. Creates DateToken, TimeToken, RomanNumeralToken, FractionToken, RangeToken,
MeasurementToken, PersonTitleToken"
  ([] (ContextDependentTokenizerAnnotator/createAnnotatorDescription)))

(defn pos-tagger
  "Part of speech tagger."
  ([] (POSTagger/createAnnotatorDescription))
  ([model] (POSTagger/createAnnotatorDescription model)))

(defn chunker
  ""
  ([] (Chunker/createAnnotatorDescription))
  ([model] (Chunker/createAnnotatorDescription model)))

(defn chunk-adjuster
  "Standard chunk adjuster. Includes AdjustNounPhraseToIncludeFollowingNP and AdjustNounPhraseToIncludeFollowingPPNP"
  ([] (ClinicalPipelineFactory/getStandardChunkAdjusterAnnotator)))

(defn dictionary-lookup-fast
  ""
  ([] (DefaultJCasTermAnnotator/createAnnotatorDescription "org/apache/ctakes/dictionary/lookup/fast/vetdict.xml"))
  ([dictionary] (DefaultJCasTermAnnotator/createAnnotatorDescription dictionary)))

(defn drug-ner
  "Finds tokens based on context. There are two major groupings or ranges that
 * will be used to create the additional annotations needed to handle the drug
  * mentions used to represent the status changes."
  ([] (uima/ae-desc DrugMentionAnnotator [DrugMentionAnnotator/PARAM_SEGMENTS_MEDICATION_RELATED (into-array String ["SIMPLE_SEGMENT"])
                                          DrugMentionAnnotator/DISTANCE "1"
                                          DrugMentionAnnotator/DISTANCE_ANN_TYPE "org.apache.ctakes.typesystem.type.textspan.Sentence"
                                          DrugMentionAnnotator/BOUNDARY_ANN_TYPE "org.apache.ctakes.typesystem.type.textspan.Sentence"])))

(defn negation
  "Rule based negation detection"
  ([] (ContextAnnotator/createAnnotatorDescription)))
