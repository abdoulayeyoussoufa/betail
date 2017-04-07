import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Police entity.
 */
class PoliceGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-XSRF-TOKEN" -> "${xsrf_token}"
    )

    val scn = scenario("Test the Police entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login")
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all police")
            .get("/api/police")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new police")
            .post("/api/police")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "raison":"SAMPLE_TEXT", "adresse":"SAMPLE_TEXT", "tel":"SAMPLE_TEXT", "email":"SAMPLE_TEXT", "interloc":"SAMPLE_TEXT", "dateDebut":"2020-01-01T00:00:00.000Z", "dateFin":"2020-01-01T00:00:00.000Z", "tiers":"SAMPLE_TEXT", "tpx":null, "activite":"SAMPLE_TEXT", "formule_soins":"SAMPLE_TEXT", "territoire":"SAMPLE_TEXT", "plafond":"0", "complement":"SAMPLE_TEXT", "consultation_taux":null, "consultation_frais":"SAMPLE_TEXT", "consultation_limite":"SAMPLE_TEXT", "consultation_jour":null, "consultation_an":null, "consultation_deux_an":null, "pharmacie_taux":null, "pharmacie_frais":"SAMPLE_TEXT", "pharmacie_limite":"SAMPLE_TEXT", "pharmacie_jour":null, "pharmacie_an":null, "pharmacie_deux_an":null, "analyse_taux":null, "analyse_frais":"SAMPLE_TEXT", "analyse_limite":"SAMPLE_TEXT", "analyse_jour":null, "analyse_an":null, "analyse_deux_an":null, "acte_taux":null, "acte_frais":"SAMPLE_TEXT", "acte_limite":"SAMPLE_TEXT", "acte_jour":null, "acte_an":null, "acte_deux_an":null, "principal_chambre_taux":null, "principal_chambre_frais":"SAMPLE_TEXT", "principal_chambre_limite":"SAMPLE_TEXT", "principal_chambre_jour":null, "principal_chambre_an":null, "principal_chambre_deux_an":null, "principal_frais_taux":null, "principal_frais_limite":"SAMPLE_TEXT", "principal_frais_jour":null, "principal_frais_an":null, "principal_frais_deux_an":null, "prive_chambre_taux":null, "prive_chambre_frais":"SAMPLE_TEXT", "prive_chambre_limite":"SAMPLE_TEXT", "prive_chambre_jour":null, "prive_chambre_an":null, "prive_chambre_deux_an":null, "prive_frais_taux":null, "prive_frais_limite":"SAMPLE_TEXT", "prive_frais_jour":null, "prive_frais_an":null, "prive_frais_deux_an":null, "public_chambre_taux":null, "public_chambre_frais":"SAMPLE_TEXT", "public_chambre_limite":"SAMPLE_TEXT", "public_chambre_jour":null, "public_chambre_an":null, "public_chambre_deux_an":null, "public_frais_taux":null, "public_frais_frais":"SAMPLE_TEXT", "public_frais_limite":"SAMPLE_TEXT", "public_frais_jour":null, "public_frais_an":null, "public_frais_deux_an":null, "soins_taux":null, "soins_frais":"SAMPLE_TEXT", "soins_limite":"SAMPLE_TEXT", "soins_jour":null, "soins_an":null, "soins_deux_an":null, "verre_taux":null, "verre_frais":"SAMPLE_TEXT", "verre_limite":"SAMPLE_TEXT", "verre_jour":null, "verre_an":null, "verre_deux_an":null, "monture_taux":null, "monture_frais":"SAMPLE_TEXT", "monture_limite":"SAMPLE_TEXT", "monture_jour":null, "monture_an":null, "monture_deux_an":null, "accouchement_taux":null, "accouchement_frais":"SAMPLE_TEXT", "accouchement_limite":"SAMPLE_TEXT", "accouchement_jour":null, "accouchement_an":"SAMPLE_TEXT", "accouchement_deux_an":null, "education_taux":null, "education_frais":"SAMPLE_TEXT", "education_limite":"SAMPLE_TEXT", "education_jour":null, "education_an":null, "education_deux_an":null, "sejour_taux":null, "sejour_frais":"SAMPLE_TEXT", "sejour_limite":"SAMPLE_TEXT", "sejour_jour":null, "sejour_an":null, "sejour_deux_an":null, "nbCollab":"0", "nbConjoint":"0", "nbEnfantP":"0", "nbEnfantG":"0", "fin":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_police_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created police")
                .get("${new_police_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created police")
            .delete("${new_police_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
