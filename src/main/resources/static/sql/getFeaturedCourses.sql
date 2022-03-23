select new com.fpt.OnlineQuiz.dto.CourseFeaturedDTO(c.id, c.name, c.subject.name, c.description, c.price, c.subject.image.src)
from Course c
where c.status = 1
order by random()

