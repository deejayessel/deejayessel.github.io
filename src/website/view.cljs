(ns website.view
  (:require [clojure.string :as str]
            [reagent.core :as reagent]))

(def last-updated "June 2021")

; Colors
(def dark-color "#333232")
(def light-color "#B7B7B7")
(def very-light-color "#f1f2f3")
(def dark-gray "#494c4d")
(def light-gray "#8d9599")

(def bkg-color "white")
(def text-color dark-color)

; Font weights
(def light-weight 300)
(def regular-weight 400)
(def medium-weight 500)
(def semi-bold-weight 600)
(def bold-weight 700)

; Font families
(def serif "Newsreader")
(def sans "Jost")

; File locations
(def curve-webm "videos/curve.webm")
(def curve-mp4 "videos/curve.mp4")
(def rainbow-webm "videos/flat-rainbow.webm")
(def rainbow-mp4 "videos/flat-rainbow.mp4")
(def face "images/david.jpeg")
(def cv "files/davidjlee_cv.pdf")
(def poster "files/synchronicity19.pdf")

(defn link
  [{url   :url
    style :style
    class :class} & children]
  [:a {:style  (merge {:font-weight "normal"} style)
       :href   url
       :class  class
       :target "_blank"
       :rel    "noopener noreferrer"}
   children])

(def small-caps
  {:font-family    sans
   :text-transform "uppercase"
   :letter-spacing "0.08em"
   :font-size      "85%"})

(defn name-emph [x]
  [:span {:style {:text-decoration "#dfe5e9 underline"}}
   x])

(defn fserif [x] 
  [:span {:style {:font-family serif}}
   x])

(defn fsans [x] 
  [:span {:style {:font-family sans}}
   x])

(defn simple-list
  [styles & items]
  [:ul {:style (merge styles
                      {:list-style   "none"
                       :padding-left 0})}
   (map-indexed (fn [i x]
                  [:li {:key i} x])
                items)])

(defn section
  [m & children]
  (let [{header :header} m]
    [:div.section (merge {:style {:margin "2.5rem 0 1.5rem"}}
                         m)
     (if (some? header)
       [:div [:h2.section_header header]])
     [:div children]]))

(defn paper
  [title authors & rest]
  [:div.paper {:style {:margin-bottom "1rem"}}
   [:div {:style {:font-weight medium-weight
                  :font-family sans}}
    title]
   [:div {:style {:margin "0 "}}
    authors]
   [:div {:style {:font-weight regular-weight}} 
    rest]])

(defn project
  [{title :title
    by    :by
    desc  :desc}]
  [:div.project {:style {:margin-bottom "1.5rem"}}
   [:h3 {:style {:margin 0}}
    title]
   (if (some? by)
     [:div.by-line {:style {}}
      by])
   [:div.description desc]])

(defn course
  [title sems]
  [:div {:style {}}
   [:span
    [:span {:style {:margin 0
                    :float  "left"}}
     title]
    " "
    [:span {:style {:font-size   "90%"
                    :font-family sans
                    :color       light-gray
                    :float       "right"}}
     sems]]
   [:div {:style {:clear "both"}}]])

(defn award
  [title year desc]
  [:div {:style {:margin-bottom "1rem"}}
   [:span
    [:span {:style {:font-family sans
                    :font-weight 500}}
     title]
    ", "
    [:span {:style {:font-family sans
                    :font-size   "90%"}}
     year
     ]
    ". "
    (if (not-empty desc)
      [:span {:style {}}
       desc ". "])
    ]])

(defn line
  ([thickness color]
   [line thickness color {}])
  ([thickness color style]
   [:div {:style (merge style {:padding-bottom 10
                               :border-top     (str thickness " solid " color)})}]))

(defn auto-play-video
  [m & sources]
  [:video (merge m
                 {:autoPlay    "true"
                  :muted       "true"
                  :playsInline "true"
                  :loop        "true"})
   sources])

(defn ar-demo []
  [:div.ar-demo {:style {:margin          "1rem 0"
                         :display         "flex"
                         :flex-direction  "row"
                         :flex-wrap       "wrap"
                         :justify-content "center"}}
   [:div.video-wrapper {:style {:margin    "1rem"
                                :max-width "400px"}}
    [auto-play-video {:style {:width      "100%"
                              :max-height "400px"
                              :max-width  "400px"
                              :padding    0}}
     [:source {:src curve-webm :type "video/webm"}]
     [:source {:src curve-mp4 :type "video/mp4"}]]
    [:p {:style {:color     light-gray
                 :font-size "smaller"}}
     "An early demo showcasing different stroke thicknesses and colors."]]
   [:div.video-wrapper {:style {:margin    "1rem"
                                :max-width "400px"}}
    [auto-play-video {:style {:width      "100%"
                              :max-height "400px"
                              :max-width  "400px"
                              :padding    0}}
     [:source {:src rainbow-webm :type "video/webm"}]
     [:source {:src rainbow-mp4 :type "video/mp4"}]]
    [:p {:style {:color     light-gray
                 :font-size "smaller"}}
     "A later demo showcasing a new brush type (flat) and color type (rainbow)."]]])

(defn app
  [{{width  :width
     height :height} :size}]
  (let [small-screen? (< width 600)]
    [:div.layout {:style {:display          "flex"
                          :flex-direction   "column"
                          :min-height       "100vh"
                          :color            text-color
                          :background-color bkg-color}}
     [:div.main {:style {:box-sizing "border-box"
                         :max-width  720
                         :margin     "0 auto"
                         :padding    "0 1rem"
                         :flex       "1 1"}}

      [:div.pic
       {:style (merge {:text-align "center"
                       :margin     "1rem"}
                      (if small-screen?
                        {:margin-bottom "1.5rem"}
                        {:margin-left "1.5rem"
                         :float       "right"}))}
       ;[:img {:src   face
       ;       :alt   "My face"
       ;       :style {:border-radius "50%"
       ;               :height        240
       ;               :width         240}}]
       ]

      [:h1.site_title "David J. Lee"]

      [section {:style {:margin-top 0}}
       [:div
        [:p {:style {:font-size "110%"}}
         "I'm an incoming Ph.D. student in computer science at Cornell University. "
         "I'm interested in programming languages, data structures, and machine learning."]
        [:p
         "Prior to Cornell, I was an undergrad at Williams College, "
         "where I wrote a thesis on filters advised by "
         [link {:url "http://cs.williams.edu/~shikha/"} "Shikha Singh"]
         " and "
         [link {:url "http://mccauleysam.com/"} "Sam McCauley"]
         "."]]
       [:div
        [simple-list {:font-family sans}
         [link {:url cv} "CV"]
         [link {:url "https://github.com/djslzx"} "GitHub"]
         [link {:url "mailto:djslzx@gmail.com"} "Email"]]]]

      [section {:header "Research"}
       [simple-list {}
        [paper
         "Telescoping Filter: A Practical Adaptive Filter"
         [:span [name-emph "David J. Lee"] ", Samuel McCauley, Shikha Singh, and Max Stein."]
         [:span "European Symposium on Algorithms (" 
          [:span {:style small-caps} 
           "ESA"] 
          "), 2021." ]]
        [paper
         "A Practical Adaptive Quotient Filter"
         [:span
          [name-emph "David J. Lee"] ". Senior thesis, 2021. "
          "Advised by " [link {:url "http://cs.williams.edu/~shikha/"} "Shikha Singh"]
          " and " [link {:url "http://mccauleysam.com/"} "Sam McCauley"] "."]]
        [paper
         [:span "Virtual Multicrossings and Petal Diagrams for Virtual Knots and Links " [link {:url "https://arxiv.org/abs/2103.08314"} "(Arxiv)"]]
         [:span 
          "Colin Adams, Chaim Even-Zohar, Jonah Greenberg, Reuben Kaufman, "
          [name-emph "David Lee"]
          ", Darin Li, Dustin Ping, Theodore Sandstrom, and Xiwen Wang. In Submission."]]
        [paper
         "Inferring Synchronization Disciplines to Verify Atomicity of Concurrent Code"
         [:span
          "Margaret Allen, " [name-emph "David J. Lee"] ". "
          [link {:url poster} "Poster"] ", 2019. "
          "With " [link {:url "http://dept.cs.williams.edu/~freund/index.html"} " Stephen Freund"]
          " for " [link {:url "http://www.cs.williams.edu/~freund/synchronicity/"} "Synchronicity"] "."]]]]

      [section {:header "Side Projects"}
       [simple-list {}
        [project {:title [:span "Learned Bloom Filters"
                          " " [link {:url "https://github.com/djslzx/learned-filters"} "(GitHub)"]]
                  :year  "Fall 2020"
                  :desc  [:p "I implemented learning-augmented Bloom filters in Python using PyTorch, "
                          "working off of two papers ["
                          [link {:url "https://arxiv.org/abs/1712.01208"} "1"]
                          ", "
                          [link {:url "https://papers.nips.cc/paper/2018/file/0f49c89d1e7298bb9930789c8ed59d48-Paper.pdf"} "2"]
                          "]. "
                          "Nominated for the 2021 Ward Prize, an annual prize awarded to the best student project in the Williams College CS Department."]}]
        [project {:title [:span "A P2P Privacy-Preserving Location-Based Proximity Tracing Protocol"
                          " " [link {:url "https://github.com/shvmsptl/footprint"} "(GitHub)"]]
                  :year  "Spring 2020"
                  :desc  [:p "A peer-to-peer digital contact tracing protocol that uses location point data (e.g. GPS) from cellular
                              devices to alert users of potential virus transmission events without compromising user anonymity.
                              Simulated in Go using Apache Cassandra. Nominated for the 2020 Ward Prize."]}]
        [project {:title [:span "Hearthstone in Lisp"]
                  :year  "Fall 2019"
                  :desc  [:p "I rewrote the Hearthstone game engine in Clojure, a Lisp hosted on the JVM, following functional programming best practices.
                              The engine core consists entirely of pure functions that are rigorously tested —
                              mutation is limited to the namespace handling the engine's interface with a web view. Contact me for code."]}]
        [project {:title [:span
                          "Augmented-Reality Drawing for iOS"
                          " " [link {:url "https://github.com/djslzx/ar-drawing"} "(GitHub)"]]
                  :year  "Fall 2018"
                  :desc  [:span
                          [:p "I wrote an iOS application that lets users draw 3D curves by moving their devices.
                               Built using ARKit (to determine device position from camera feed) and SceneKit (to generate 3D geometries)."]
                          [ar-demo]]}]
        ]]
      (comment 
        [section {:header "Teaching"}
         [:div [:h3 "Williams College (Teaching Assistant)"]
          [simple-list {}
           [course "Principles of Programming Languages" "S21, F20, F19, S19"]
           [course "Software Methods" "S20"]
           [course "Intro to Computer Science" "F18"]
           [course "Data Structures" "S18"]]]])
      (comment
        [section {:header "Academic Honors"}
         [simple-list {}
          [award "Sam Goldberg Colloquium Prize in Computer Science" 2021
           "Awarded for the best student colloquium in computer science at Williams College"]
          [award "Sigma Xi" 2021 ""]
          [award "Phi Beta Kappa (Junior Year)" 2020 "Awarded to top 5% of graduating class by GPA"]
          ]])]

     [:div.footer {:style {:text-align       "center"
                           :margin-top       "3rem"
                           :padding          "1rem 0"
                           :width            "100%"
                           :font-size        "0.75rem"
                           :color            dark-gray
                           :background-color bkg-color}}
      "Last updated " last-updated ". "
      "Written in ClojureScript " [link {:url   "https://github.com/djslzx/djslzx.github.io"
                                         :style {:text-decoration "none"}}
                                   "(Source)"]
      "."]]))
