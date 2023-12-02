#lang racket
(define (ldisplayln . in) (for-each display in) (newline))
(define (compare left right)
  (cond
    [(equal? left right) 'same]
    [(and (number? left) (number? right)) (< left right)]
    [(and (number? left) (list? right)) (compare (list left) right)]
    [(and (list? left) (number? right)) (compare left (list right))]
    [(and (empty? left) (not (empty? right))) #t]
    [(and (empty? right) (not (empty? left))) #f]
    [else
      (define test (compare (car left) (car right)))
      (if (equal? test 'same)
        (compare (cdr left) (cdr right))
        test
      )
    ]
  )
)
(define (compFile progress)
  (define line (read-line))
  (cond
    [(eof-object? line) progress]
    [(= (string-length line) 0) (compFile progress)]
    [else
      (define first (with-input-from-string (string-replace line "," " ") read))
      (define second (with-input-from-string (string-replace (read-line) "," " ") read))
      (compFile (append progress (list (compare first second))))
    ]
  )
)
(define (part1)
  (define pairChecks (compFile '()))
  (define goodIndices (filter (lambda (n) (list-ref pairChecks (- n 1))) (inclusive-range 1 (length pairChecks))))
  (ldisplayln "Part #1 - " (apply + goodIndices))
)
(define (getAllFile progress)
  (define line (read-line))
  (cond
    [(eof-object? line) progress]
    [(= (string-length line) 0) (getAllFile progress)]
    [else (getAllFile (append progress (list (with-input-from-string (string-replace line "," " ") read))))]
  )
)
(define (part2)
  (define sorted (sort (append (getAllFile '()) (list '((2)) '((6)))) compare))
  (ldisplayln "Part #2 - " (* (+ (index-of sorted '((2))) 1) (+ (index-of sorted '((6))) 1)))
)
(with-input-from-file "input.txt" part1 #:mode 'text)
(with-input-from-file "input.txt" part2 #:mode 'text)