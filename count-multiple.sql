SELECT count(*) FILTER (WHERE is_correct)
  FROM answer as a INNER JOIN quiz_history_question qhq
                              on a.answer_id = qhq.user_answer WHERE qhq.quiz_history_id = 6) * 1.0 /
(SELECT count(*)  FROM answer as a INNER JOIN quiz_history_question qhq
 on a.answer_id = qhq.user_answer WHERE qhq.quiz_history_id = 6) * 100 as percent