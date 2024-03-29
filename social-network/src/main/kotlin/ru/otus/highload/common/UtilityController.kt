//package ru.otus.highload.common
//
//import org.springframework.http.HttpStatus
//import org.springframework.web.bind.annotation.CrossOrigin
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.ResponseStatus
//import org.springframework.web.bind.annotation.RestController
//import ru.otus.highload.chats.core.db.ChatRepository
//import ru.otus.highload.chats.core.db.FriendChatEntity
//import ru.otus.highload.chats.core.db.MessageDtoEntity
//import ru.otus.highload.posts.core.db.persistence.PostEntity
//import ru.otus.highload.posts.core.db.persistence.PostRepository
//import java.time.LocalDateTime
//import java.util.*
//
//
//@RestController
//@RequestMapping("/utility")
//@CrossOrigin
//class UtilityController(private val repository: PostRepository,
//                        private val chatRepository: ChatRepository) {
//
//    private companion object {
//        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/generate/posts")
//    fun addFriend(@RequestParam("userId") userId: String, @RequestParam("postsNumber") number: Int) {
//        for (i in 0..number) {
//            repository.upsertForUtility(
//                    PostEntity(
//                            postId = UUID.randomUUID().toString(),
//                            userId = userId,
//                            postText = generateString(),
//                            createDate = LocalDateTime.now().minusMinutes((500..1000).random().toLong()),
//                            modifyDate = LocalDateTime.now().minusMinutes((0..500).random().toLong())
//                    )
//            )
//        }
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/generate/chats")
//    fun addMessage(@RequestParam("userId") userId: String,
//                   @RequestParam("chats_number") number: Int,
//                   @RequestParam("friendId") friendId: String) {
//        val chatId = UUID.randomUUID().toString()
//        chatRepository.saveChat(
//                FriendChatEntity(
//                        chatId = chatId,
//                        fromUserId = userId,
//                        toUserId = friendId,
//                        created = LocalDateTime.now().minusMinutes((500..1000).random().toLong())
//                )
//        )
//        for (i in 0..number) {
//            chatRepository.saveMessage(
//                    MessageDtoEntity(
//                            messageId = UUID.randomUUID().toString(),
//                            chatId = chatId,
//                            fromUserId = userId,
//                            toUserId = friendId,
//                            text = generateString(),
//                            created = LocalDateTime.now().minusMinutes((0..500).random().toLong())
//                    )
//            )
//        }
//    }
//
////    @ResponseStatus(HttpStatus.CREATED)
////    @PostMapping("/generate/chats/messages")
////    fun addMessagestoChats() {
////        chatRepository.getAllChatIds().
////        for (i in 0..number) {
////            chatRepository.saveMessage(
////                    MessageDtoEntity(
////                            messageId = UUID.randomUUID().toString(),
////                            chatId = chatId,
////                            fromUserId = userId,
////                            toUserId = friendId,
////                            text = generateString(),
////                            created = LocalDateTime.now().minusMinutes((0..500).random().toLong())
////                    )
////            )
////        }
////    }
//
//    private fun generateString(): String = List(23) { charPool.random() }.joinToString("") + " " +
//            List(3) { charPool.random() }.joinToString("") + " " +
//            List(13) { charPool.random() }.joinToString("")
//
//}
//
//
