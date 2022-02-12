SELECT new com.fpt.OnlineQuiz.dto.CourseFeaturedDTO(c.id, c.name, c.subject.name, c.description, c.price, c.subject.image.src)
FROM Course c
ORDER BY random()

