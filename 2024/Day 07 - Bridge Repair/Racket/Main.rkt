#lang racket

(define (readfile)
  (define (readfile-inner result)
    (define line (read-line))
    (cond
      [(eof-object? line) result]
      [(= (string-length (string-trim line)) 0) result]
      [else
        (readfile-inner (append result (list (map string->number (string-split (string-trim line) #px"[^\\d]+")))))
      ]
    )
  )
  (readfile-inner (list))
)

(define (part1 input)
  (define (attempt-row row ops goal)
    (define-values (restOps op) (quotient/remainder ops 2))
    (cond
      [(> (car row) goal) #f]
      [(> (length row) 1)
        (case op
          [(1) (attempt-row (cons (* (car row) (cadr row)) (cddr row)) restOps goal)]
          [(0) (attempt-row (cons (+ (car row) (cadr row)) (cddr row)) restOps goal)]
        )
      ]
      [else (= (car row) goal)]
    )
  )
  (define (attempt-row-countdown row index)
    (cond
      [(> 0 index) #f]
      [(attempt-row (cdr row) index (car row)) #t]
      [else (attempt-row-countdown row (- index 1))]
    )
  )
  (define (attempt-row-outer row)
    (attempt-row-countdown row (- (expt 2 (- (length row) 2)) 1))
  )
  (apply + (map car (filter attempt-row-outer input)))
)

(define (part2 input)
  (define (attempt-row row ops goal)
    (define-values (restOps op) (quotient/remainder ops 3))
    (cond
      [(> (car row) goal) #f]
      [(> (length row) 1)
        (case op
          [(2) (attempt-row (cons (string->number (string-append (number->string (car row)) (number->string (cadr row)))) (cddr row)) restOps goal)]
          [(1) (attempt-row (cons (* (car row) (cadr row)) (cddr row)) restOps goal)]
          [(0) (attempt-row (cons (+ (car row) (cadr row)) (cddr row)) restOps goal)]
        )
      ]
      [else (= (car row) goal)]
    )
  )
  (define (attempt-row-countdown row index)
    (cond
      [(> 0 index) #f]
      [(attempt-row (cdr row) index (car row)) #t]
      [else (attempt-row-countdown row (- index 1))]
    )
  )
  (define (attempt-row-outer row)
    (attempt-row-countdown row (- (expt 3 (- (length row) 2)) 1))
  )
  (apply + (map car (filter attempt-row-outer input)))
)

(define input (with-input-from-file "input.txt" readfile #:mode 'text))
(display "Part #1 - ")
(displayln (part1 input))
(display "Part #2 - ")
(displayln (part2 input))