import com.optum.giraffle.tasks.GsqlTask

plugins {
    id("com.optum.giraffle") version "1.0.2" // (1)
    id("net.saliman.properties") version "1.4.6"
}

repositories {
    jcenter()
}

val gsqlGraphname: String by project // (1)
val gsqlHost: String by project
val gsqlUsername: String by project
val gsqlPassword: String by project
val gsqlAdminUsername: String by project
val gsqlAdminPassword: String by project
val tokenMap: LinkedHashMap<String, String> = linkedMapOf("graphname" to gsqlGraphname) // (2)
val grpSchema: String = "Tigergraph Schema"


tigergraph {
    tokens.set(tokenMap)
}

tigergraph {
    scriptDir.set(file("db_scripts"))
    tokens.set(tokenMap)
    serverName.set(gsqlHost)
    userName.set(gsqlUsername)
    password.set(gsqlPassword)
    adminUserName.set(gsqlAdminUsername)
    adminPassword.set(gsqlAdminPassword)
}

val createSchema by tasks.creating(GsqlTask::class) {
    group = grpSchema
    description = "Create the schema on the database"
    // scriptPath = "/Users/dmccrea1/Documents/workspace/umls-giraffle/db_scripts/schema/schema.gsql" // (4)
    scriptPath = "schema/schema.gsql" // (4)
    superUser = true // (5)
}

tasks.create<GsqlTask>("dropSchema") { // (8)
    group = grpSchema
    description = "Drops the schema on the database"
    scriptPath = "schema/drop.gsql"
    superUser = true
}

tasks.create<GsqlTask>("showSchema") { // (8)
    group = grpSchema
    description = "List all the items in the database"
    scriptPath = "schema/show_schema.gsql"
    superUser = true
}


//echo {
//   println("Running echo task")
//}

