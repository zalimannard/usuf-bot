<div class="myWrapper" align="center" markdown="1">

# :musical_note: Usuf Bot

*Музыкальный бот для Дискорда*

[![Open issues](https://img.shields.io/github/issues/zalimannard/usuf-bot)](https://github.com/zalimannard/usuf-bot/issues)
[![Stars](https://img.shields.io/github/stars/zalimannard/usuf-bot)](https://github.com/zalimannard/usuf-bot)
[![Licence](https://img.shields.io/github/license/zalimannard/usuf-bot)](https://github.com/zalimannard/usuf-bot/blob/main/LICENSE)
[![Donate](https://img.shields.io/badge/donate-(money)-blueviolet)](https://boosty.to/zalimannard)

</div>

Замена боту Rythm, которого заблокировал Ютуб [Terms of Service - II. Prohibitions](https://developers.google.com/youtube/terms/api-services-terms-of-service).

## :black_nib: Команды

| Команда            | Аргументы                            | Описание                                                               |
|--------------------|--------------------------------------|------------------------------------------------------------------------|
| `play`, `p`        | `URL`, `Запрос`                      | Добавить трек(и) в конец очереди                                       |
| `skip`, `s`        |                                      | Пропустить текущий трек                                                |
| `info`, `i`        | `_`, `№`                             | Информация о текущем/указанном треке                                   |
| `jump`, `j`        | `№`                                  | Перейти к указанному треку                                             |
| `queue`, `q`       | `_`, `[n]`, `b [n]`, `all`           | Показать 10/n треков после/до текущего и всю очередь                   |
| `insert`, `in`     | `URL`, `№ URL`, `№ Запрос`, `Запрос` | Вставить трек по ссылке/запросу после текущего/указанного трека        |
| `remove`, `r`      | `№`, `-№`, `№-`, `№-№`               | Удалить трек/треки до/треки после/указанные                            |
| `prev`, `pr`       | `HH:MM:SS`                           | Перейти к предыдущему треку                                            |
| `rewind`, `rw`     |                                      | Перемотать трек к указанной позиции. Точность указывайте сколько нужно |
| `shuffle`, `sh`    |                                      | Перемешать треки в очереди                                             |
| `loop`, `l`        |                                      | Зациклить текущий трек                                                 |
| `loopq`, `lq`      |                                      | Зациклить очередь                                                      |
| `clear`, `c`       |                                      | Очистить очередь                                                       |
| `save`             | `Название`, `Название~Описание`      | Сохранить текущую очередь                                              |
| `show`             | `_`, `№`                             | Показать список сохранённых очередей/вывести 10 треков указанной       |
| `load`             | `№`                                  | Запустить указанную сохранённую очередь                                |
| `deletesavedqueue` | `№`                                  | Удалить очередь из сохранённых                                         |
| `help`, `h`        |                                      | Открыть это меню                                                       |
| `hardreset`        |                                      | Полное принудительное выключение. Не показывается в help               |

## :computer: Запуск

Потребуется Java 19 и 7.6. Может запустится и на других, но я не проверял

```shell
git clone https://github.com/zalimannard/usuf-bot.git
cd usuf-bot
nvim docker-compose.yml
# *Напишите файл конфигурации. Пример ниже*
docker-compose up -d
```

### Пример docker-compose.yml

```yml
version: '3.8'

services:
  usuf-bot:
    container_name: usuf-bot
    image: zalimannard/usuf-bot
    environment:
      - PREFIX=-
      - TOKEN=MTAyMzY5MzYyMzk2NTAwMzgyNg.G4Xv2E.pYfbxWh
    volumes:
      - ./queues/:/opt/usuf-bot/queues/
    restart: unless-stopped
```

## :syringe: Зависимости

- [JDA](https://github.com/DV8FromTheWorld/JDA) - для создания бота
- [lavaplayer](https://github.com/sedmelluq/lavaplayer) - для воспроизведения аудио

- [java-youtube-downloader](https://github.com/sealedtx/java-youtube-downloader) - для скачивания с Ютуба

## :pencil: Лицензия

Проект под [MIT](https://github.com/zalimannard/usuf-bot/blob/main/LICENSE) лицензией.
