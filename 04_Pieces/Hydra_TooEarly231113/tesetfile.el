;;; tesetfile.el --- Description -*- lexical-binding: t; -*-
;;
;; Copyright (C) 2023 Iannis Zannos
;;
;; Author: Iannis Zannos <https://github.com/iani>
;; Maintainer: Iannis Zannos <zannos@gmail.com>
;; Created: November 14, 2023
;; Modified: November 14, 2023
;; Version: 0.0.1
;; Keywords: abbrev bib c calendar comm convenience data docs emulations extensions faces files frames games hardware help hypermedia i18n internal languages lisp local maint mail matching mouse multimedia news outlines processes terminals tex tools unix vc wp
;; Homepage: https://github.com/iani/tesetfile
;; Package-Requires: ((emacs "24.3"))
;;
;; This file is not part of GNU Emacs.
;;
;;; Commentary:
;;
;;  Description
;;
;;; Code:

 (defun check-if-saturday ()
  (let ((day (nth-value 6 (decode-time (get-universal-time)))))
    (if (= day 6)
        (message "Today is Saturday!")
        (message "Today is not Saturday."))))











(provide 'tesetfile)
;;; tesetfile.el ends here
