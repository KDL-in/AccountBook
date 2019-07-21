# 导出jar
1. 第三方jar使用的时候放再工程目录下lib里，右键添加到lib就可以批量添加第3方jar，没必要一个一个加（应该是可以改名字的，不过这样比较规范，这里用了什么名字，下边就跟这用）
2. project structure->artifacts->+->设置一下主类
3. 注意，这个时候要把第三方jar删掉，要不然exe4j会找不到主类，不知道为什么会这样但是
4. build->build artifacts就行了
# exe4j
这里主要是一些重要的设置
1. 创建目录，将jre（64位），lib（第三方jar放的地方，lib名字和上边一致)还有生成的jar包放于目录下
1. 用于生成的目录，项目名字，程序名最好一样，为避免一些没必要的错误
2. Executable info 下 change working directory to:填入. 方便相对路径使用
3. 同上，64位jre要勾上64位
4. 同上，Manifest options可以禁用dpi缩放
5. java invocation 下 Classpath的设置非常重要，需要添加生成的jar，以及所有用到的第三方包，这样exe4j就会把所有包都打包成一个exe
6. 同上，设置好Main class，jar生成正确能自动检索到
7. configure JRE 下，设置jre路径，选择创建目录下的jre
# 一些重要的地方
1. java to exe 的时候，第三方jar包的用法应该是，首先得放在工程目录下的某个目录中，导出jar时没必要导出这些，等到exe4j中，在创建的目录的相同文件夹下，把所有jar都添加，让exe4j打包
2. 这里的重要的原理是，创建的这个文件夹就相当于工程文件夹，而两者都是相对目录的重要部分：两者都是相对目录的根文件夹
