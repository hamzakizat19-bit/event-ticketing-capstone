# Кезеңдер (M1–M5) және GitHub жоспары

Код толық дайын. Бірақ Capstone тек кодты емес, **GitHub тарихын** да бағалайды (10 балл және жеке түзету). Сондықтан кодты main-ге бір коммитпен жібермеңіз. Төмендегі жоспар бойынша бөліп енгізіңіз: әр бөлікке issue → branch → PR → шолу → merge, ал әр кезеңнің соңында тег қойылады.

Әр студенттің кемінде **10 мағыналы коммиті** және кемінде **2 шолудан өткен PR-ы** болуы керек.

## M1: Негіз (1–2 апта) → тег `v0.1`

| Issue | Branch | Файлдар |
|---|---|---|
| Жоба қаңқасы, CI | `chore/11-setup` | `pom.xml`, `.github/`, `.vscode/`, `.gitignore`, `scripts/` |
| 10 user story | `docs/12-user-stories` | `docs/USER_STORIES.md` (+ әр story-ге GitHub issue) |
| ADR-0001 қабаттар | `docs/13-adr-layers` | `docs/adr/0001-*.md`, `ArchitectureTest.java` |

GitHub-та істелетіндер: private репо жасау, оқытушыны collaborator ретінде шақыру, main-ді қорғау (PR + 1 approve + CI), Project тақтасын жасау (Backlog / In progress / In review / Done) және M1…M5 milestone-дарын ашу.

## M2: Негізгі домен (3–6 апта) → тег `v0.2`

| Issue | Branch | Үлгі | Файлдар |
|---|---|---|---|
| Орындар картасы | `feature/14-seat-map` | Composite | `domain/venue/Seat*`, `SeatGroup`, `GroupKind`, `SeatStatus`, `SeatCategory` + тест |
| Іс-шара, Repository (жад) | `feature/15-events-repo` | Repository | `domain/event`, `domain/repository`, `infra/persistence/InMemory*`, `EventService` |
| Билеттер | `feature/16-ticket-factory` | Factory Method | `domain/ticket/*` + `TicketBridgeTest` (алғашқы нұсқа) |
| Промокод | `feature/17-promo-decorator` | Decorator | `domain/pricing/Price*`, `*Discount`, `domain/promo` |
| Хабарламалар | `feature/18-notifications` | Observer | `service/notify`, `infra/notify` |
| Брондау + консоль | `feature/19-booking-console` | — | `BookingService` (hold/pay/cancel), `ConsoleUI`, `App` |

## 7-апта: Үлгілер аудиті

| Issue | Branch | Файлдар |
|---|---|---|
| Аудит | `docs/20-pattern-audit` | `docs/adr/0009-*.md`, `docs/PATTERN_REGISTER.md` |

## M3: Күрделі үлгілер (8–11 апта) → тег `v0.3`

| Issue | Branch | Үлгі |
|---|---|---|
| Тарифтер | `feature/21-pricing-strategy` | Strategy (+ Decorator үйлесімі), ADR-0003 |
| Брондау күйлері | `feature/22-booking-state` | State, ADR-0006 |
| Алаң құрастырушы | `feature/23-venue-builder` | Builder, ADR-0002 |
| Билет форматтары | `feature/24-ticket-bridge` | Bridge, ADR-0005 |
| Кеңейту нүктесі | `test/25-extension-point` | `ExtensionPointTest` |

## M4: Архитектура және сақтау (12–14 апта) → тег `v0.4`

| Issue | Branch | Үлгі |
|---|---|---|
| Брондауды тексеру | `feature/26-validation-chain` | Chain of Responsibility, ADR-0007 |
| Ұстап тұру / қайтару | `feature/27-hold-memento` | Memento, ADR-0004 |
| Файлда сақтау | `feature/28-file-repository` | Repository (2-нұсқа), ADR-0008 |
| Қамту ≥ 60% | `test/29-coverage` | JaCoCo шегі |

## M5: Релиз (15 апта) → тег `v1.0`

| Issue | Branch | Файлдар |
|---|---|---|
| Қабылданбаған үлгілер | `docs/30-rejected-patterns` | ADR-0010, 0011, 0012 |
| Рефлексиялар | `docs/31-reflections` | `docs/reflection-<handle>.md` (әр студент **өзі** жазады) |
| README, презентация | `docs/32-release` | `README.md`, `docs/PRESENTATION.md` |

## Командалар

```powershell
git switch main; git pull
git switch -c feature/14-seat-map
# ... файлдарды қосу, шағын коммиттер ...
git commit -m "feat: add Composite seat map (#14)"
mvn verify
git push -u origin feature/14-seat-map
# GitHub-та PR ашу: "Closes #14", екінші адам шолады
```

Релиз кезінде:

```powershell
git switch main; git pull
git tag -a v0.2 -m "M2: core domain"
git push origin v0.2
```

## Қорғауға дайындық (тікелей өзгеріс сұрауы)

Оқытушы қорғауда жаңа талап беруі мүмкін. Ықтимал сұраулар және оларды қай жерде қосу керегі:

| Сұрау | Не жазу керек |
|---|---|
| Жаңа жеңілдік түрі | жаңа `PricingStrategy` немесе жаңа `PriceDecorator` + `PromoType` мәні |
| Жаңа хабарлама арнасы (Telegram) | жаңа `BookingListener` + `App`-та `subscribe` |
| Жаңа билет форматы (PDF/QR) | жаңа `TicketFormat` + `App`-тағы тізімге қосу |
| Жаңа ереже (бір адамға 1 бронь) | жаңа `BookingRule` + `App`-тағы тізбекке қосу |
| Жаңа есеп (сектор бойынша сатылым) | `EventService`-те жаңа әдіс немесе жеке `ReportService` |
