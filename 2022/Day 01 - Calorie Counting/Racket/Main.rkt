#lang racket
(define (main)
  (define (readfile results current)
    (define line (read-line))
    (cond
      [(eof-object? line) results]
      [(= (string-length line) 0) (readfile (append results (list (foldl + 0 current))) `())]
      [else (readfile results (append current (list (string->number line))))]
    )
  )
  (define (ldisplayln . in) (for-each display in) (newline))
  (define sorted (sort (readfile '() `()) >))
  (ldisplayln "Part #1 - " (first sorted))
  (ldisplayln "Part #2 - " (foldl + 0 (take sorted 3)))
)
(with-input-from-file "input.txt" main #:mode 'text)