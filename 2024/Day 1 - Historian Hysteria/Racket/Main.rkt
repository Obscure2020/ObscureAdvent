#lang racket

(define left '())
(define right '())

(define (readfile)
  (define line (read-line))
  (if (eof-object? line)
    (void)
    (begin
      (let* (
          [parts (regexp-split #px"\\s+" line)]
          [leftPart (string->number (car parts))]
          [rightPart (string->number (cadr parts))]
        )
        (set! left (append left (list leftPart)))
        (set! right (append right (list rightPart)))
      )
      (readfile)
    )
  )
)

(define (part1 total left right)
  (cond
    [(empty? left) total]
    [(empty? right) total]
    [else
      (define diff (abs (- (car left) (car right))))
      (part1 (+ total diff) (cdr left) (cdr right))
    ]
  )
)

(define (part2 total left right)
  (cond
    [(empty? left) total]
    [else
      (define (match? num) (= num (car left)))
      (define bump (* (count match? right) (car left)))
      (part2 (+ total bump) (cdr left) right)
    ]
  )
)

(with-input-from-file "input.txt" readfile #:mode 'text)
(set! left (sort left <))
(set! right (sort right <))
(display "Part #1 - ")
(displayln (part1 0 left right))
(display "Part #2 - ")
(displayln (part2 0 left right))