<div class="myWrapper" align="center" markdown="1">

# :musical_note: Usuf Bot

*Self-hosted Discord music bot*

[![Open issues](https://img.shields.io/github/issues/zalimannard/usuf-bot)](https://github.com/zalimannard/usuf-bot/issues)
[![Stars](https://img.shields.io/github/stars/zalimannard/usuf-bot)](https://github.com/zalimannard/usuf-bot)
[![Licence](https://img.shields.io/github/license/zalimannard/usuf-bot)](https://github.com/zalimannard/usuf-bot/blob/main/LICENSE)
[![Donate](https://img.shields.io/badge/donate-(money)-blueviolet)](https://boosty.to/zalimannard)

</div>

The music bot we used was blocked by YouTube. As a local alternative, I made this bot. The project is made for educational purposes, all actions you do at your own risk. Using a bot to listen to Youtube videos is contrary to the [Terms of Service - II. Prohibitions](https://developers.google.com/youtube/terms/api-services-terms-of-service).

## :black_nib: Commands

| Name | Arguments | Description |
| - | - | - |
| `play` | `URL` | Add a track/playlist to the end of the queue using the link |
| `clear` |  | Clear the queue |
| `help` |  | Show the help menu |

## :computer: Start

### Build and run from source

```shell
git clone https://github.com/zalimannard/usuf-bot.git
cd usuf-bot
gradle build -x test
cd build/libs
java -jar usuf-bot.jar INSERT_YOUR_PREFIX_HERE INSERT_YOUR_TOKEN_HERE
```

### Don't you have a token? Get it now!

1. Go to the [Discord Developer Portal](https://discord.com/developers) and log in
2. Click "New Application" and enter a name. Later you will be able to change the name of the bot
3. Click "Bot" in the left menu.
4. Click "Add bot" on the right
5. Activate the "MESSAGE CONTENT INTENT" switch
6. Click "Reset token" and your token will appear

### Do you have a token, but the bot is not on the server? Let's add it!

1. Click "OAuth2" on the [Discord Developer Portal](https://discord.com/developers)
2. Click "URL Generator"
3. SCOPES > bot
4. BOT PERMISSIONS > Administrator
5. Follow the link that appears
6. Select your Discord server

## :notebook: Documentation

I would like to place Javadoc somehow in a smart way. But now I haven't come up with anything better than generating it from source:
```shell
git clone https://github.com/zalimannard/usuf-bot.git
cd usuf-bot
gradle javadoc
```
and open the file `./build/docs/javadoc/index.html`

## :syringe: Dependencies

- [JDA](https://github.com/DV8FromTheWorld/JDA) - to create a discord bot
- [lavaplayer](https://github.com/sedmelluq/lavaplayer) - to work with audio files

- [java-youtube-downloader](https://github.com/sealedtx/java-youtube-downloader) - to work with YouTube

## :pager: Supported platforms

- YouTube

Adding new platforms is very simple. You need to take a library for it and make a wrapper. That is, to implement the Platform interface. If you want, you can do it yourself, or find a library for me and send an Issue.

## :dancers: Contributing

Want to help develop Usuf Bot? Download the sources, change them and send pull requests.

If you find an issue, please report it on the [issue tracker](https://github.com/docker/compose/issues/new/choose).

## :pencil: Licence

This project is licensed under [MIT](https://github.com/zalimannard/usuf-bot/blob/main/LICENSE) license.
