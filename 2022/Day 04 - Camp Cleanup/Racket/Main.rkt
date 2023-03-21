#lang racket
(define (main)
  (define (ldisplayln . in) (for-each display in) (newline))
  (define (readLoop contains overlaps)
    (define read (read-line))
    (cond
      [(eof-object? read) (cons contains overlaps)]
      [else
        (let* (
          [lineSplit (map string->number (string-split read #rx",|-"))]
          [one (first lineSplit)]
          [two (list-ref lineSplit 1)]
          [three (list-ref lineSplit 2)]
          [four (last lineSplit)]
          [contCheck (or (and (>= one three) (<= one four) (>= two three) (<= two four)) (and (>= three one) (<= three two) (>= four one) (<= four two)))]
          [cont (if contCheck 1 0)]
          [overCheck (or (and (>= one three) (<= one four)) (and (>= two three) (<= two four)) (and (>= three one) (<= three two)) (and (>= four one) (<= four two)))]
          [over (if overCheck 1 0)]
          )
          (readLoop (+ contains cont) (+ overlaps over))
        )
      ]
    )
  )
  (define resultsPair (readLoop 0 0))
  (ldisplayln "Part #1 - " (car resultsPair))
  (ldisplayln "Part #2 - " (cdr resultsPair))
)
(with-input-from-file "input.txt" main #:mode 'text)