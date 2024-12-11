#lang racket

(define (readfile)
  (map string->number (regexp-split #px" " (string-trim (read-line))))
)

(define (stringHalves str)
  (define halfLen (/ (string-length str) 2))
  (list (substring str 0 halfLen) (substring str halfLen))
)

(define (singleStone num)
  (define strNum (number->string num))
  (cond
    [(= num 0) (list 1)]
    [(even? (string-length strNum)) (map string->number (stringHalves strNum))]
    [else (list (* num 2024))]
  )
)

(define stoneResults (make-hash))

(define (procStones stones blinks)
  (define (procStone stone)
    (define id (cons stone blinks))
    (define lookup (dict-ref stoneResults id null))
    (cond
      [(= blinks 0) 1]
      [(not (null? lookup)) lookup]
      [else        
        (define result (procStones (singleStone stone) (- blinks 1)))
        (dict-set! stoneResults id result)
        result
      ]
    )
  )
  (apply + (map procStone stones))
)

(define initial (with-input-from-file "input.txt" readfile #:mode 'text))
(display "Part #1 - ")
(displayln (procStones initial 25))
(display "Part #2 - ")
(displayln (procStones initial 75))