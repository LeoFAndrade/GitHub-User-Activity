# GitHub User Activity

A small command-line tool that fetches the recent public activity of a GitHub user and prints it in the terminal.

Built as a solution to the [GitHub User Activity](https://roadmap.sh/projects/github-user-activity) project from roadmap.sh.

```
$ github-activity octocat
Pushed commits to octocat/Hello-World 6 times
Starred octocat/Spoon-Knife
Opened a new issue in octocat/Hello-World
```

## Features

- Fetches events from `https://api.github.com/users/<username>/events`.
- Turns each event into a readable sentence.
- Groups identical actions on the same repository and shows how many times they happened.
- Handles invalid usernames, users without recent activity, and API/network failures with clear messages and proper exit codes.
- No external libraries: HTTP is done with Java's built-in `HttpClient`, and the JSON parsing is written by hand.

### Supported event types

| Event | Output |
| --- | --- |
| `PushEvent` | `Pushed commits to <repo>` |
| `WatchEvent` | `Starred <repo>` |
| `ForkEvent` | `Forked <repo>` |
| `PublicEvent` | `Made <repo> public` |
| `CreateEvent` | `Created a new branch, tag or repository in <repo>` |
| `IssuesEvent` | `Opened a new issue in <repo>` (or `Updated an issue in <repo> (<action>)`) |
| anything else | `Did <EventType> in <repo>` |

Events are printed in the order the API returns them (most recent first).

## Requirements

- Java 11 or newer (the project uses `java.net.http.HttpClient`)
- Maven 3.x

## Build

The Maven project lives in the `github-user-activity/` folder:

```
cd github-user-activity
mvn clean package
```

This produces `target/github-activity.jar`.

## Usage

```
java -jar target/github-activity.jar <username>
```

On Windows, there is also a launcher script next to the `pom.xml` that does the same thing:

```
.\github-activity.cmd <username>
```

To run it as just `github-activity <username>` from any folder, add the project folder (the one containing `github-activity.cmd` and `target/`) to your `PATH`. This is optional and specific to your machine. Remember to rebuild with `mvn clean package` after changing the code, since the script runs the jar in `target/`.

### Exit codes

| Situation | Message | Exit code |
| --- | --- | --- |
| Activity found | The grouped list | `0` |
| User exists but has no recent public activity | `No recent activity found for this user.` | `0` |
| No username provided | `Error: No username provided.` | `1` |
| User does not exist (HTTP 404) | `User: <username> not found.` | `1` |
| Other API or network failure | `An error occurred: ...` | `1` |

## Project structure

```
github-user-activity/src/main/java/com/leofandrade/githubuseractivity/
├── App.java                   Entry point: wires everything and handles errors
├── cli/UserCli.java           Reads the username from the command-line arguments
├── client/GitHubApiClient.java  Performs the HTTP request to the GitHub API
├── dto/GitHubEventDto.java    Holds the parsed data of one event
├── exception/UserNotFoundException.java  Raised on HTTP 404
└── service/
    ├── JsonParser.java        Hand-written, generic JSON splitting helpers
    └── AssignmentService.java Extracts the relevant fields and formats sentences
```

The parser does not build a full JSON tree. It splits arrays into objects and objects into key/value fields while tracking brace depth and quoted strings, and then only the fields the tool needs (`type`, `repo.name`, `payload.action`) are read.

## Known limitations

- **Only recent public events.** The GitHub events endpoint exposes a limited window of public activity, and unauthenticated requests are rate limited by GitHub.
- **No commit counts.** The `PushEvent` payload returned by the API did not include the list of commits, so the tool cannot say "Pushed 3 commits". Identical pushes are counted instead (`... 6 times`).
- **No pagination** and no authentication.
- **JSON parser edge cases.** Because the parser is hand-written, unusual text inside values can confuse it. In particular, the step that splits the top-level array into events counts braces without looking at quotes, so an unbalanced `{` or `}` inside a text field (for example an issue title) can produce wrong output. Escaped quotes (`\"`) are not handled specially either.
- Only the first command-line argument is used.

## License

See [LICENSE](LICENSE).