SELECT new com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO(e.account.id, e.account.fullName, e.description, e.account.profileImage.src)
FROM Expert e
ORDER BY random()