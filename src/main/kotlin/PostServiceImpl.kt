import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import io.ktor.http.*
import java.io.File
import kotlin.text.get

class PostServiceImpl(
    private val client: HttpClient
): PostService {
    override suspend fun getPosts(): List<PostResponse> {

        return try {
            client.get{
                url(HttpRoutes.POSTS)
            }
        } catch (e: RedirectResponseException){
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {



        return try {
            client.post<PostResponse>{
                url(HttpRoutes.POSTS)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        } catch (e: RedirectResponseException){
            println("Error: ${e.message}")
            null
        }

    }
}