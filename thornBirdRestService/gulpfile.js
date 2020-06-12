// 引入gulp组件
var gulp = require('gulp'),					// gulp基础库
	uglify=require('gulp-uglify'),			// js压缩
	minifycss=require('gulp-minify-css'),	// css压缩
	concat=require('gulp-concat'),			// 合并文件
	rename=require('gulp-rename'),			// 文件重命名
	jshint=require('gulp-jshint'),			// js检查
	notify=require('gulp-notify');			// 提示

var del = require('del'),
	resources = "src/main/resources/static/",
	config = {
		cssPattern: 'css/**/*.css',
		jsPattern:  'js/**/*.js',
		minPattern: 'min/'
	};

gulp.task('default', function() {
});

//compress:css
gulp.task('compress:css',function () {
	return gulp.src(resources + config.cssPattern)     //设置css
		.pipe(concat('thornBird.min.css'))      		//合并css文件
		.pipe(gulp.dest(resources + 'min'))           	//设置输出路径
//		.pipe(rename({suffix:'.min'}))         			//修改文件名
		.pipe(minifycss())                    			//压缩文件
		.pipe(gulp.dest(resources + 'min'))            	//输出文件目录
		.pipe(notify({message:'css task ok'}));   		//提示成功
});

//compress:js
gulp.task('compress:js',function () {
	return gulp.src(resources + config.jsPattern)  //选择合并的JS
		.pipe(concat('thornBird.min.js'))   		//合并js
		.pipe(gulp.dest(resources + 'min'))    		//输出路径
//		.pipe(rename({suffix:'.min'}))     			//重命名
		.pipe(uglify())                    			//压缩
		.pipe(gulp.dest(resources + 'min'))         //输出文件目录 
		.pipe(notify({message:"js task ok"}));    	//提示
});

//compress
gulp.task('compress', gulp.series('compress:css', 'compress:js'));

//watch
gulp.task('watch', gulp.series('compress:css', 'compress:js', function () {
	return gulp
		//Watch the input folder and run a task when changes occur
		.watch([resources + config.cssPattern, resources + config.jsPattern], gulp.series('compress:css', 'compress:js'))
		//When change occurs output a message
		.on('change', function (event) {
			console.log('File ' + event.path + ' was ' + event.type + ', file updated...');
		});
	})
);

//clean
gulp.task('clean', function () {
  return del([
      resources + config.minPattern
  ]);
});