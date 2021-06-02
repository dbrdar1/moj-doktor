import React from 'react';
import { BellOutlined, LogoutOutlined} from '@ant-design/icons';
import '../assets/css/naslovna.css'
import { useHistory } from 'react-router';

const HeaderNaslovna = (props) => {
    let history = useHistory();
    const logout = () =>{
        localStorage.setItem("token", "");
        localStorage.setItem("uloga", "");
        localStorage.setItem("id", -1);
        history.push("/")
    }

    return(
        <div className="naslovna-header">
                <h2 className="pocetna"> {props.stranica} </h2>
                <div className="header-elementi">
                    <BellOutlined style={{color: "grey"}} /> &nbsp;&nbsp;
                    <LogoutOutlined style={{color: "grey"}} onClick={logout} /> &nbsp;
                </div>
            </div>
    );
}

export default HeaderNaslovna;