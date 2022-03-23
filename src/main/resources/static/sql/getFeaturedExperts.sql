select new com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO(e.account.id, e.account.fullName, e.description, e.account.profileImage.src)
from Expert e
where e.account.status = 1
order by random()
