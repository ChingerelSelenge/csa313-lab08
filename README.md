# Lab 08 — Mockito Unit Testing

## 1. Лабораторийн ажлын мэдээлэл

- **Оюутны нэр:** С.Чингэрэл
- **Оюутны код:** B232270010

## 2. Лабораторийн ажлын зорилго

Mockito framework ашиглан Java программын unit test бичих, хамааралтай объектуудыг mock хийх, mock объектын үйлдлийг шалгах, exception боловсруулах болон parameterized test ашиглах чадвар эзэмших.

## 3. Ашигласан орчин, технологи

| Технологи | Хувилбар |
|---|---|
| Java | OpenJDK 21.0.12.1 |
| Maven | 3.9.9 |
| JUnit Jupiter | 5.10.2 |
| Mockito JUnit Jupiter | 5.11.0 |
| Maven Surefire | 3.2.5 |
| Operating System | Debian GNU/Linux |

## 4. Төслийн бүтэц

```text
csa313-lab08/
├── src/
│   ├── main/
│   │   └── java/mn/edu/must/sqat/
│   │       ├── Student.java
│   │       ├── StudentRepository.java
│   │       ├── EmailSender.java
│   │       └── CourseRegistrationService.java
│   └── test/
│       └── java/mn/edu/must/sqat/
│           └── CourseRegistrationServiceTest.java
├── results/
│   ├── mvn-test.txt
│   ├── mvn-test-mutant-1.txt
│   ├── mvn-test-mutant-2.txt
│   └── mvn-test-mutant-3.txt
├── .gitignore
├── pom.xml
└── README.md
```

## 5. Хэрэгжүүлсэн тестүүд (A1–A8)

Тестүүдийг `CourseRegistrationServiceTest.java` файлд хэрэгжүүлсэн.

| Тест | Тайлбар | Mockito ашиглалт |
|---|---|---|
| A1 | Идэвхтэй оюутан хичээлд амжилттай бүртгүүлэх | `when()`, `verify()` |
| A2 | Оюутан олдохгүй үед алдаа буцаах | `thenReturn(null)`, `never()` |
| A3 | Идэвхгүй оюутны бүртгэлийг хориглох | `verifyNoInteractions()` |
| A4 | Урьдчилсан нөхцөл хангагдаагүй үед бүртгэхгүй байх | `never()` |
| A5 | Имэйл илгээхэд exception гарсан ч бүртгэлийг амжилттай хадгалах | `doThrow()` |
| A6 | Null утга дамжуулахад `IllegalArgumentException` үүсэх | `assertThrows()`, `verifyNoInteractions()` |
| A7 | Имэйл илгээсэн body дотор хичээлийн код байгаа эсэхийг шалгах | `ArgumentCaptor` |
| A8 | Бүртгэлийн гурван төрлийн алдааг параметрчилсэн тестээр шалгах | `@ParameterizedTest`, `@CsvSource`, `lenient()` |

### A8 — lenient() ашигласан тайлбар

Parameterized test-ийн мөр бүрд ижил mock тохиргоо ашиглагдах боловч тухайн мөрийн нөхцөлөөс шалтгаалан зарим тохиргоо ашиглагдахгүй байж болно. Mockito-ийн strict stubbing горимд ашиглагдаагүй тохиргоо `UnnecessaryStubbingException` үүсгэх эрсдэлтэй тул тухайн stubbing тохиргоонд `lenient()` ашигласан.

## 6. Тестийн үр дүн

Эцсийн тестийг дараах командаар ажиллуулсан:

```bash
mvn test
```

**Эцсийн үр дүн:**

```text
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Нийт 10 тест амжилттай ажиллаж, алдаа болон алгассан тест гараагүй.

Дэлгэрэнгүй үр дүнг `results/mvn-test.txt` файлаас харж болно.

## 7. Mutation Testing

Mutation testing ашиглан тестүүд программын логик дахь өөрчлөлтийг илрүүлж чадаж байгаа эсэхийг шалгасан. Туршилт бүрд эх кодын нэг нөхцөл эсвэл үйлдлийг түр өөрчилж, тест ажиллуулсны дараа эх кодыг буцаан сэргээсэн.

| № | Mutation хийсэн өөрчлөлт | Илрүүлсэн тест | Failures | Үр дүн |
|---|---|---|---:|---|
| 1 | Идэвхгүй оюутны шалгалтыг устгасан | A3, A8 | 2 | BUILD FAILURE |
| 2 | `saveRegistration()` үйлдлийг устгасан | A1, A5 | 2 | BUILD FAILURE |
| 3 | `courseId == null` шалгалтыг устгасан | A6 | 1 | BUILD FAILURE |

Гурван туршилтаар тестүүд өөрчлөгдсөн логикийг илрүүлж чадсан. Туршилт бүрийн дараа эх кодыг хэвийн байдалд нь буцааж, бүх тестийг дахин ажиллуулсан.

Эцсийн байдлаар:

```text
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Mutation test-ийн дэлгэрэнгүй log файлууд `results/` хавтсанд хадгалагдсан.

## 8. Дүгнэлт

Энэхүү лабораторийн ажлаар Mockito ашиглан Java программын unit test бичиж сурлаа. Mock объект нь бодит хамааралтай объектын оронд ашиглагдаж, тестийн үед тухайн объектын буцаах утга болон үйлдлийг удирдах боломж олгодог. Харин Fake объект нь бодит объектын зарим үйлдлийг энгийн байдлаар дуурайлган хэрэгжүүлдэг. Гаднын үйлчилгээ, өгөгдлийн сан, имэйл илгээх зэрэг хамаарлыг mock хийх нь тестийг тусгаарлахад тохиромжтой. Харин тестэлж байгаа үндсэн логик болон энгийн өгөгдлийн объектуудыг шаардлагагүйгээр mock хийх хэрэггүй. `verify()`-г хэт их ашиглавал программын үр дүнгээс илүү дотоод хэрэгжүүлэлтийн нарийн зүйлд тест хамааралтай болж, код өөрчлөгдөхөд тестүүд амархан эвдэрч болно. Мөн mutation testing хийснээр бичсэн тестүүд алдаатай өөрчлөлтийг илрүүлж чадаж байгаа эсэхийг шалгалаа. Эцсийн байдлаар нийт 10 тест амжилттай ажиллаж, гурван mutation туршилт бүгд тестийн алдаагаар илэрсэн.

## 9. Ажиллуулах заавар

Төслийг татаж аваад үндсэн хавтас руу орно:

```bash
git clone https://github.com/ChingerelSelenge/csa313-lab08.git
cd csa313-lab08
```

Бүх тестийг ажиллуулах:

```bash
mvn test
```

Тестийн үр дүнг файлд хадгалах:

```bash
mkdir -p results
mvn test 2>&1 | tee results/mvn-test.txt
```

## 10. GitHub Repository

[https://github.com/ChingerelSelenge/csa313-lab08](https://github.com/ChingerelSelenge/csa313-lab08)
