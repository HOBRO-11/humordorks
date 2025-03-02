// TXT 파일 경로
const txtFilePath = 'videos.txt';

// 버튼 생성 함수
function createButtons(videoData) {
    const buttonsContainer = document.getElementById('buttons-container');

    videoData.forEach(video => {
        // 버튼 컨테이너 생성
        const buttonContainer = document.createElement('div');
        buttonContainer.className = "buttons-container"; // 중간 컨테이너 스타일 클래스

        // 버튼 생성
        const button = document.createElement('button');
        button.className = "dynamic-button"; // 버튼에 클래스 추가
        button.textContent = video.title; // 버튼 내용 설정

        // 클릭 이벤트 추가
        button.addEventListener('click', function () {
            toggleVideo(this, video.url);
        });

        // 영상 컨테이너 생성
        const videoContainer = document.createElement('div');
        videoContainer.className = "video-container"; // 동영상 컨테이너 클래스

        // iframe 생성
        const videoIframe = document.createElement('iframe');
        videoIframe.src = ""; // 초기에 빈 src
        videoIframe.frameBorder = "0";
        videoIframe.allow = "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture";
        videoIframe.allowFullscreen = true;

        // iframe을 영상 컨테이너에 추가
        videoContainer.appendChild(videoIframe);

        // 버튼과 영상 컨테이너를 버튼 컨테이너에 추가
        buttonContainer.appendChild(button);
        buttonContainer.appendChild(videoContainer);

        // 전체 버튼 영역에 추가
        buttonsContainer.appendChild(buttonContainer);
    });
}

// URL 변환 함수
function convertToEmbedUrl(url) {
    const urlObj = new URL(url);
    if (urlObj.hostname === "www.youtube.com" || urlObj.hostname === "youtube.com") {
        const videoId = urlObj.searchParams.get("v"); // `v` 파라미터 값 추출
        if (videoId) {
            return `https://www.youtube.com/embed/${videoId}`;
        }
    } else if (urlObj.hostname === "youtu.be") {
        const videoId = urlObj.pathname.substring(1); // `youtu.be/ID`에서 ID 추출
        return `https://www.youtube.com/embed/${videoId}`;
    }
    return url; // 변환되지 않는 경우 원본 URL 반환
}

// 버튼 클릭 시 영상 토글 함수
function toggleVideo(button, url) {
    const videoContainer = button.nextElementSibling; // 버튼 다음 요소(영상 컨테이너) 가져오기
    const iframe = videoContainer.querySelector('iframe');

    if (videoContainer.style.display === "none" || videoContainer.style.display === "") {
        // URL 변환
        const embedUrl = convertToEmbedUrl(url);

        // 영상 펼치고 URL 설정 후 재생
        videoContainer.style.display = "block";
        iframe.src = embedUrl + (embedUrl.includes("?") ? "&autoplay=1" : "?autoplay=1");
    } else {
        // 영상 숨기고 재생 중단
        iframe.src = ""; // 영상 src 초기화
        videoContainer.style.display = "none";
    }
}

// TXT 파일에서 데이터 불러오기
fetch(txtFilePath)
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to load TXT file');
        }
        return response.text();
    })
    .then(text => {
        const videoData = parseTxtFile(text); // TXT 내용을 파싱
        createButtons(videoData); // 버튼 생성
    })
    .catch(error => {
        console.error('Error loading TXT:', error);
    });

// TXT 파일 파싱 함수
function parseTxtFile(text) {
    const lines = text.split('\n').map(line => line.trim()); // 줄 단위로 나누고 양쪽 공백 제거
    const videoData = [];
    let tempTitle = null;

    lines.forEach(line => {
        if (line.startsWith('http') || line.startsWith('https')) {
            // URL 행이면 제목과 함께 저장
            if (tempTitle) {
                videoData.push({ title: tempTitle, url: line });
                tempTitle = null;
            }
        } else if (line) {
            // 제목 행이면 임시로 저장
            tempTitle = line;
        }
    });

    return videoData;
}
