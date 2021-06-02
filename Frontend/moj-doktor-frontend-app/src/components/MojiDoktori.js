import PacijentSideBar from './PacijentSideBar';
import NaslovnaPregledKartica from './NaslovnaPregledKartica';
import '../assets/css/pacijenti-doktori.css'
import HeaderNaslovna from './HeaderNaslovna';
import Carousel from 'react-multi-carousel';
import 'react-multi-carousel/lib/styles.css';
import { message } from 'antd';
import React, { useState, useEffect } from 'react';
import Stranica403 from './stranica403';
import Loader from './Loader';


const MojiDoktori = () => {
    let [loading, setLoading] = useState(true);

    const responsive = {
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 3,
            slidesToSlide: 3 // optional, default to 1.
        }
    }

    const [podaciDoktora, setPodaciDoktora] = useState([]);

    useEffect(() => {
        if (localStorage.getItem("uloga") !== 'PACIJENT') return;
        const headers = new Headers({ "Authorization": 'Bearer ' + localStorage.getItem("token") });
        fetch('http://localhost:8080/pregledi-kartoni/doktori-pacijenta/' + localStorage.getItem("id"), { method: "get", headers })
            .then((res) => {
                return res.json();
            })
            .then((res) => {
                res.forEach(doktor => {
                    setPodaciDoktora(p => [...p, doktor])
                });
                setLoading(false);
            })
            .catch(() => {
                message.error("Došlo je do greške.");
            });
    }, []);

    if (localStorage.getItem("uloga") !== 'PACIJENT') return (<Stranica403 />)
    return (
        <PacijentSideBar>
            <HeaderNaslovna stranica="Moji doktori" />
            {loading ? <Loader /> :
                <div >
                    <Carousel responsive={responsive} containerClass="carousel-container">
                        {podaciDoktora.map((doktor, index) => (
                            <NaslovnaPregledKartica key={doktor.id} podaciDoktora={doktor} doktor={false}>
                            </NaslovnaPregledKartica>
                        ))}
                    </Carousel>
                </div>}
        </PacijentSideBar>

    );
}

export default MojiDoktori;