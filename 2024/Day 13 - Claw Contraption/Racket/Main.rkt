#lang typed/racket
(require math/matrix)

(struct clawMachine ([goal-x : Integer] [goal-y : Integer] [a-bump-x : Integer] [a-bump-y : Integer] [b-bump-x : Integer] [b-bump-y : Integer]))

(define (string->integer [input : String]) : Integer
  (assert (string->number input) exact-integer?)
)

(define (readfile) : (Listof clawMachine)
  (define (readfile-internal [results : (Listof clawMachine)] [waiting : (Listof String)]) : (Listof clawMachine)
    (define line (read-line))
    (cond
      [(eof-object? line) results]
      [(= (string-length (string-trim line)) 0) (readfile-internal results waiting)]
      [(< (length waiting) 2) (readfile-internal results (append waiting (list (string-trim line))))]
      [else
        (define buttonA (map string->integer (string-split (car waiting) #px"[^\\d]+")))
        (define buttonB (map string->integer (string-split (cadr waiting) #px"[^\\d]+")))
        (define prize (map string->integer (string-split (string-trim line) #px"[^\\d]+")))
        (readfile-internal (append results (list (clawMachine (car prize) (cadr prize) (car buttonA) (cadr buttonA) (car buttonB) (cadr buttonB)))) '())
      ]
    )
  )
  (readfile-internal '() `())
)

(define (small-cost [cm : clawMachine]) : Integer
  (define-values (result info) (matrix-gauss-elim (matrix [[(clawMachine-a-bump-x cm) (clawMachine-b-bump-x cm) (clawMachine-goal-x cm)] [(clawMachine-a-bump-y cm) (clawMachine-b-bump-y cm) (clawMachine-goal-y cm)]]) #t #t))
  (define a-presses (matrix-ref result 0 2))
  (define b-presses (matrix-ref result 1 2))
  (cond
    [(not (exact-integer? a-presses)) 0]
    [(not (exact-integer? b-presses)) 0]
    [else
      (+ (* a-presses 3) b-presses)
    ]
  )
)

(define (large-cost [cm : clawMachine]) : Integer
  (define increased-cm (struct-copy clawMachine cm [goal-x (+ (clawMachine-goal-x cm) 10000000000000)] [goal-y (+ (clawMachine-goal-y cm) 10000000000000)]))
  (small-cost increased-cm)
)

(define arcade (with-input-from-file "input.txt" readfile #:mode 'text))
(display "Part #1 - ")
(displayln (apply + (map small-cost arcade)))
(display "Part #2 - ")
(displayln (apply + (map large-cost arcade)))