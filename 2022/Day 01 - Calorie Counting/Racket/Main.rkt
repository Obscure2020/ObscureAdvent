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
  (define sorted (sort (readfile '() `()) >))
  (display "Part #1 - ")
  (displayln (first sorted))
  (display "Part #2 - ")
  (displayln (foldl + 0 (take sorted 3)))
)
(with-input-from-file "input.txt" main #:mode 'text)